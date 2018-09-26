package com.axmor;

import com.axmor.employee.*;
import com.axmor.issue.*;
import com.axmor.login.LoginController;
import com.axmor.sql2omodel.ConnectDB;
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
    public static ConnectDB connectDB;

    public static void main(String[] args) {

        // Instantiate your dependencies
        issueDao = new IssueDao();
        employeeDao = new EmployeeDao();
        connectDB = new ConnectDB();
        //Configure spark
        port(80);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();
        issueDao.issues = connectDB.model.getAllIssues();
        employeeDao.employees = connectDB.model.getAllEmployees();
        //        get("/", (Request req, Response res) ->
//                "<html><body><h1>Hello, world!</h1></body></html>");
        // Set up before-filters (called before each get/post)
        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);
        // Set up routes
        get(Path.Web.ISSUES, IssueController.fetchAllIssues);
        get(Path.Web.ONE_ISSUE, IssueController.fetchOneIssue);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        get(Path.Web.SIGNUP, LoginController.serveSignUpPage);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.SIGNUP, LoginController.handleSignUpPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get("*", ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*", Filters.addGzipHeader);
    }
}
