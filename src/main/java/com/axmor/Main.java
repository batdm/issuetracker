package com.axmor;

import com.axmor.employee.*;
import com.axmor.issue.*;
import com.axmor.issue_log.IssueLogDao;
import com.axmor.login.LoginController;
import com.axmor.sql2omodel.ConnectDB;
import com.axmor.status.StatusDao;
import com.axmor.util.Filters;
import com.axmor.util.Path;
import com.axmor.util.ViewUtil;
import spark.Redirect;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Application entry point
 */
public class Main {

    // Declare dependencies
    public static IssueDao issueDao;
    public static ConnectDB connectDB;
    public static IssueLogDao issueLogDao;
    public static StatusDao statusDao;

    public static void main(String[] args) {

        // Instantiate your dependencies
        issueDao = new IssueDao();
        EmployeeDao employeeDao = new EmployeeDao();
        connectDB = new ConnectDB();
        issueLogDao = new IssueLogDao();
        statusDao = new StatusDao();
        //Configure spark
        port(80);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();
        issueDao.issues = connectDB.model.getAllIssues();
        employeeDao.employees = connectDB.model.getAllEmployees();
        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);
        redirect.get("/", Path.Web.LOGIN);
        // Set up routes
        get(Path.Web.ISSUES, IssueController.fetchAllIssues);
        get(Path.Web.ONE_ISSUE, IssueController.fetchOneIssue);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        get(Path.Web.SIGNUP, LoginController.serveSignUpPage);
        get(Path.Web.CREATE_ISSUE, IssueController.serveCreateIssue);
        post(Path.Web.ONE_ISSUE, IssueController.handleOneIssue);
        post(Path.Web.CREATE_ISSUE, IssueController.handleCreateIssue);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.SIGNUP, LoginController.handleSignUpPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get("*", ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*", Filters.addGzipHeader);
    }
}
