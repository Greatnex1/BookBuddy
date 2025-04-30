package com.ilearn.book_buddy.specifications;

import com.ilearn.book_buddy.helpers.AppendableReferenceUtils;
import com.ilearn.book_buddy.helpers.DateTimeUtils;
import com.ilearn.book_buddy.helpers.EnumUtils;
import jakarta.persistence.criteria.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ilearn.book_buddy.constants.ErrorMessages.*;
import static com.ilearn.book_buddy.specifications.ObjectParser.*;

public class CriteriaConverter {

    private final static Pattern FULLTEXT_PATTERN = Pattern.compile(FULLTEXT + ":(.*?),");
    private final static Pattern QUERY_PATTERN = Pattern.compile("(.*?)(:<|:>|:|<|>|~|-)(.*?)([,|])");

    public static List<QueryToCriteriaImpl.Criteria> queryToCriteria(String query, String... ignoreParameters) {
        List<QueryToCriteriaImpl.Criteria> criteriaList = new ArrayList<>();

        if (!StringUtils.hasText(query)) {
            return criteriaList;
        }

        String decodedQuery = parseValue(query);
        Matcher matcher = QUERY_PATTERN.matcher(decodedQuery + ",");
        while (matcher.find()) {
            String key = matcher.group(1);
            String condition = matcher.group(2);
            String value = parseValue(matcher.group(3));
            String operatorValue = matcher.group(4);
            // set operator
            QueryToCriteriaImpl.Operator operator = Objects.equals(operatorValue, OR_OPERATOR) ? QueryToCriteriaImpl.Operator.OR : QueryToCriteriaImpl.Operator.AND;

            if (key != null) {
                if(Arrays.asList(ignoreParameters).contains(key)) continue;

                if (key.equals(ID)) {
//                    long id = AppendableReferenceUtils.getIdFrom(value);
                    String id = AppendableReferenceUtils.getReferenceFrom(value);
                    String reference = AppendableReferenceUtils.getReferenceFrom(value);
                    criteriaList.add(new QueryToCriteriaImpl.Criteria(key, condition, id, QueryToCriteriaImpl.Operator.AND));
                    criteriaList.add(new QueryToCriteriaImpl.Criteria(REFERENCE, condition, reference, operator));
                    continue;
                } else if (key.toLowerCase().endsWith(ID)) {
                    String id = AppendableReferenceUtils.getReferenceFrom(value);
                    criteriaList.add(new QueryToCriteriaImpl.Criteria(key, condition, id, operator));
                    continue;
                } else if (key.equalsIgnoreCase(FULLTEXT)) continue;
                criteriaList.add(new QueryToCriteriaImpl.Criteria(key, condition, value, operator));
            }
        }
        return criteriaList;
    }

    public static String getFullTextValue(String query) {
        Matcher matcher = FULLTEXT_PATTERN.matcher(query + ",");
        if (matcher.find()) return matcher.group(1);
        return null;
    }

    public static void fullTextAsPredicate(QueryToCriteria<?> queryToCriteria, ArrayList<Predicate> predicates,
                                           CriteriaBuilder criteriaBuilder, Root<?> root) {
        String fullTextValue = queryToCriteria.getFullTextValue();
        Set<String> columns = queryToCriteria.getFullTextAttributes();
        if (fullTextValue == null || fullTextValue.trim().isEmpty() || columns == null || columns.isEmpty()) return;

        String value = fullTextValue.toLowerCase();
        Predicate[] predicateArray = new Predicate[columns.size()];
        int index = 0;
        for (String column : columns) {
            Expression<String> attributeString;
            if (column.contains(".")) {
                String[] keys = column.split("\\.");
                int length = keys.length;
                if (keys.length <= 0) return;

                Join<?, ?> objectJoin = root.join(keys[0], JoinType.LEFT);
                for (int i = 1; i < length - 1; i++) {
                    objectJoin = objectJoin.join(keys[i], JoinType.LEFT);
                }

                String lastKey = keys[length - 1];
                Expression<?> attribute = objectJoin.get(lastKey);
                attributeString = criteriaBuilder.lower(attribute.as(String.class));
            } else {
                attributeString = criteriaBuilder.lower(root.get(column).as(String.class));
            }
            predicateArray[index] = criteriaBuilder.like(attributeString, "%" + value + "%");
            index++;
        }
        predicates.add(criteriaBuilder.or(predicateArray));
    }

    /**
     * Convert a query criteria into a Predicate for specifications. This function in turn calls a similar function to
     * prepare the predicate
     * @param criteria The criteria to be converted
     * @param root The root of the specification
     * @param criteriaBuilder The criteria builder of the entity to be specified for
     * @return Predicate based on the provided criteria
     */
    public static Predicate asPredicate(QueryToCriteria.Criteria criteria, Root<?> root, CriteriaBuilder criteriaBuilder) {
        if (criteria.getKey().contains(QUERY_PROP_SEPARATOR))
            return prepareJoinPredicate(criteriaBuilder, root, criteria);
        return asPredicate(criteriaBuilder, root.get(criteria.getKey()), criteria);
    }

    /**
     * Taking in a criteria builder, attribute, condition and value, this function converts the parameters into a
     * Predicate
     * @param criteriaBuilder The criteria builder of the entity to be specified for
     * @param attribute The root attribute of the specification
     * @param criteria The criteria to be converted
     * @return Predicate for the criteria specification
     */
    private static Predicate asPredicate(CriteriaBuilder criteriaBuilder, Expression<?> attribute, QueryToCriteriaImpl.Criteria criteria) {
        String condition = criteria.getCondition();
        Object value = criteria.getValue();
        if(value == null) return criteria.nextPredicate(criteriaBuilder);

        // cast class if an Enum/Date is provided
        Class<?> classType = attribute.getJavaType();
        if (classType.isEnum()) {
            value = EnumUtils.toEnum(classType, value);
        } else if (classType == Date.class) {
            value = DateTimeUtils.fromMillis(String.valueOf(value));
        } else if (classType == boolean.class || classType == Boolean.class){
            value = Boolean.parseBoolean(String.valueOf(value));
        }

        switch (condition) {
            case EQUAL -> {
                return criteriaBuilder.equal(attribute, value);
            }
            case GREATER_THAN -> {
                if (classType == Date.class) {
                    return criteriaBuilder.greaterThan(attribute.as(Date.class), (Date) value);
                } else if (classType == Long.class || classType == long.class) {
                    return criteriaBuilder.greaterThan(attribute.as(Long.class), parseLong(value));
                } else if (classType == Integer.class || classType == int.class) {
                    return criteriaBuilder.greaterThan(attribute.as(Integer.class), parseInteger(value));
                } else if (classType == BigDecimal.class) {
                    return criteriaBuilder.greaterThan(attribute.as(BigDecimal.class), parseBigDecimal(value));
                }
                return criteriaBuilder.greaterThan(attribute.as(String.class), String.valueOf(value));
            }
            case GREATER_THAN_INCLUSIVE -> {
                if (classType == Date.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(attribute.as(Date.class), (Date) value);
                } else if (classType == Long.class || classType == long.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(attribute.as(Long.class), parseLong(value));
                } else if (classType == Integer.class || classType == int.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(attribute.as(Integer.class), parseInteger(value));
                } else if (classType == BigDecimal.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(attribute.as(BigDecimal.class), parseBigDecimal(value));
                }
                return criteriaBuilder.greaterThanOrEqualTo(attribute.as(String.class), String.valueOf(value));
            }
            case LESSER_THAN -> {
                if (classType == Date.class) {
                    return criteriaBuilder.lessThan(attribute.as(Date.class), (Date) value);
                } else if (classType == Long.class || classType == long.class) {
                    return criteriaBuilder.lessThan(attribute.as(Long.class), parseLong(value));
                } else if (classType == Integer.class || classType == int.class) {
                    return criteriaBuilder.lessThan(attribute.as(Integer.class), parseInteger(value));
                } else if (classType == BigDecimal.class) {
                    return criteriaBuilder.lessThan(attribute.as(BigDecimal.class), parseBigDecimal(value));
                }
                return criteriaBuilder.lessThan(attribute.as(String.class), String.valueOf(value));
            }
            case LESSER_THAN_INCLUSIVE -> {
                if (classType == Date.class) {
                    return criteriaBuilder.lessThanOrEqualTo(attribute.as(Date.class), (Date) value);
                } else if (classType == Long.class || classType == long.class) {
                    return criteriaBuilder.lessThanOrEqualTo(attribute.as(Long.class), parseLong(value));
                } else if (classType == Integer.class || classType == int.class) {
                    return criteriaBuilder.lessThanOrEqualTo(attribute.as(Integer.class), parseInteger(value));
                } else if (classType == BigDecimal.class) {
                    return criteriaBuilder.lessThanOrEqualTo(attribute.as(BigDecimal.class), parseBigDecimal(value));
                }
                return criteriaBuilder.lessThanOrEqualTo(attribute.as(String.class), String.valueOf(value));
            }
            case LIKE -> {
                return criteriaBuilder.like(criteriaBuilder.lower(attribute.as(String.class)),
                        "%" + String.valueOf(value).toLowerCase() + "%");
            }
            case NOT_EQUAL -> {
                return criteriaBuilder.notEqual(attribute, value);
            }
            default -> {
                return criteria.nextPredicate(criteriaBuilder);
            }
        }
    }

    private static Predicate prepareJoinPredicate(CriteriaBuilder criteriaBuilder, Root<?> root,
                                                  QueryToCriteriaImpl.Criteria criteria) {
        String key = criteria.getKey();
        String[] keys = key.split("\\.");
        int length = keys.length;
        if (keys.length <= 0) return criteria.nextPredicate(criteriaBuilder);

        Join<?, ?> objectJoin = root.join(keys[0], criteria.getJoinType());
        for (int i = 1; i < length - 1; i++) {
            objectJoin = objectJoin.join(keys[i], criteria.getJoinType());
        }

        String lastKey = keys[length - 1];
        return asPredicate(criteriaBuilder, objectJoin.get(lastKey), criteria);
    }}
