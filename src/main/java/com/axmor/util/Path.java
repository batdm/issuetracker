package com.axmor.util;

import lombok.Getter;

public class Path {
    public static class Web {
        @Getter
        public static final String START = "/";
        @Getter
        public static final String LOGIN = "/login/";
        @Getter
        public static final String SIGNUP = "/signup/";
        @Getter
        public static final String LOGOUT = "/logout/";
        @Getter
        public static final String ISSUES = "/issues/";
        @Getter
        public static final String ONE_ISSUE = "/issues/:issue_id/";
        @Getter
        public static final String CREATE_ISSUE = "/create_issue/";
    }

    public static class Template {
        public final static String LOGIN = "/velocity/login/login.vm";
        public final static String SIGNUP = "/velocity/login/signup.vm";
        public final static String ISSUE_ALL = "/velocity/issue/all.vm";
        public static final String ISSUE_ONE = "/velocity/issue/one.vm";
        public static final String CREATE_ISSUE = "/velocity/issue/create.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }
}
