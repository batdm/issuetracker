package com.axmor.util;

import spark.Request;

public class RequestUtil {

    public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }

    public static String getQueryLogin(Request request) {
        return request.queryParams("login");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

    public static String getQueryLoginRedirect(Request request) {
        return request.queryParams("loginRedirect");
    }

    public static String getParamIssueId(Request request) {
        return request.params("issue_id");
    }

    public static String getQueryIssueName(Request request) {
        return request.queryParams("name");
    }

    public static String getQueryStatus(Request request) {
        return request.queryParams("status");
    }

    public static String getQueryComment(Request request) {
        return request.queryParams("comment");
    }

    public static String getQueryIssueDescription(Request request) {
        return request.queryParams("description");
    }


    public static String getSessionLocale(Request request) {
        return request.session().attribute("locale");
    }

    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    public static String getSessionCreatedUser(Request request) {
        return request.session().attribute("createdUser");
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }
}
