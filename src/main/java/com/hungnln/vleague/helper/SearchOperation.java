package com.hungnln.vleague.helper;

public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS, ISMEMBER, GREATER_THAN_DATE, LESS_THAN_DATE,
    GREATER_THAN_OR_EQUAL_DATE, LESS_THAN_OR_EQUAL_DATE;

    public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~", "in" };

    public static SearchOperation getSimpleOperation(char input) {
        switch (input) {
            case ':':
                return EQUALITY;
            case '!':
                return NEGATION;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case '~':
                return LIKE;
            case 'i':
                return ISMEMBER;
            default:
                return null;
        }
    }
}
