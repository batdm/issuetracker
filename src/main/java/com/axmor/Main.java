package com.axmor;

import com.axmor.employee.*;
import com.axmor.issue.*;
import com.axmor.login.LoginController;
import com.axmor.util.Filters;
import com.axmor.util.Path;
import com.axmor.util.ViewUtil;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Application entry point
 */
public class Main {

    // Declare dependencies
    public static IssueDao issueDao;
    public static EmployeeDao employeeDao;

    public static void main(String[] args) {

        // Instantiate your dependencies
        issueDao = new IssueDao();
        employeeDao = new EmployeeDao();

        //Configure spark
        port(80);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();
//        get("/", (Request req, Response res) ->
//                "<html><body><h1>Hello, world!</h1></body></html>");
        // Set up before-filters (called before each get/post)
        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);
        // Set up routes
//        get(Path.Web.INDEX,          IndexController.serveIndexPage);
        get(Path.Web.ISSUES, IssueController.fetchAllIssues);
        get(Path.Web.ONE_ISSUE, IssueController.fetchOneIssue);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get("*", ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*", Filters.addGzipHeader);
    }
}
