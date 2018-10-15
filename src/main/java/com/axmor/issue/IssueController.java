package com.axmor.issue;

import com.axmor.employee.Employee;
import com.axmor.employee.EmployeeController;
import com.axmor.login.LoginController;
import com.axmor.util.Path;
import com.axmor.util.ViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static com.axmor.Main.connectDB;
import static com.axmor.Main.issueDao;
import static com.axmor.Main.issueLogDao;
import static com.axmor.Main.statusDao;
import static com.axmor.util.JsonUtil.*;
import static com.axmor.util.RequestUtil.*;
import static spark.Spark.get;

public class IssueController {
    private final static Logger logger = LoggerFactory.getLogger(IssueController.class);
    public static Route fetchAllIssues = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("issues", issueDao.getAllIssues());
            return ViewUtil.render(request, model, Path.Template.ISSUE_ALL);
        }
        if (clientAcceptsJson(request)) {
            response.status(200);//http://sparkjava.com/tutorials/sql2o-database
            return dataToJson(issueDao.getAllIssues());
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };
    public static Route fetchOneIssue = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            Issue issue = connectDB.model.getIssueById(getParamIssueId(request));
            Employee employee = connectDB.model.getEmployeeByLogin(issue.getEmployee_login());
            model.put("issue", issue);
            model.put("employee", employee);
            model.put("issue_logs", issueLogDao.getAllIssueLogs(issue.getName()));
            model.put("empty_log", issueLogDao.issueLogSize());
            model.put("Allstatus", statusDao.getAllStatus());
            logger.info("get one issue");
            if (getSessionIncorrectString(request)) {//isCorrectLogin is not good method name for this case, but the logic is perfect. The comment content verification on contains spec chars. My English is not good=)
                model.put("specCharExist", true);
                logger.info("get one issue incorrect name");
                return ViewUtil.render(request, model, Path.Template.ISSUE_ONE);
            }
            return ViewUtil.render(request, model, Path.Template.ISSUE_ONE);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(connectDB.model.getIssueById(getParamIssueId(request)));
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route handleOneIssue = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        Issue issue = connectDB.model.getIssueById(getParamIssueId(request));
        Employee employee = connectDB.model.getEmployeeByLogin(issue.getEmployee_login());
        model.put("issue", connectDB.model.getIssueById(getParamIssueId(request)));
        model.put("employee", employee);
        model.put("issue_logs", issueLogDao.getAllIssueLogs(issue.getName()));
        model.put("empty_log", issueLogDao.issueLogSize());
        model.put("Allstatus", statusDao.getAllStatus());
//        if (EmployeeController.isIncorrectLogin(getQueryComment(request))) {//isCorrectLogin is not good method name for this case, but the logic is perfect. The comment content verification on contains spec chars. My English is not good=)
//            model.put("specCharExist", true);
//            request.session().attribute("isCorrectString", true);
//            response.redirect(Path.Web.ISSUES.concat(issue.getIssue_id()), 303);// решает проблему повторной отправки формы при обновлении страницы
//            return ViewUtil.render(request, model, Path.Template.ISSUE_ONE);
//        }
        request.session().attribute("isCorrectString", false);
        connectDB.model.createComment(issue.getName(), getSessionCurrentUser(request), getQueryStatus(request), getQueryComment(request));
        get(Path.Web.ONE_ISSUE, IssueController.fetchOneIssue);
        response.redirect(Path.Web.ISSUES.concat(issue.getIssue_id()), 303);// решает проблему повторной отправки формы при обновлении страницы
        return ViewUtil.render(request, model, Path.Template.ISSUE_ONE);
    };

    public static Route serveCreateIssue = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.CREATE_ISSUE);
    };

    public static Route handleCreateIssue = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        if (connectDB.model.getIssueByName(getQueryIssueName(request)) != null) {
            model.put("issueAlreadyExist", true);
            return ViewUtil.render(request, model, Path.Template.CREATE_ISSUE);
        }
//        else if (EmployeeController.isIncorrectLogin(getQueryIssueName(request))) {//isCorrectLogin is not good method name for this case, but the logic is perfect. The issue name verification on contains spec chars. My English is not good=)
//            model.put("specCharExist", true);
//            return ViewUtil.render(request, model, Path.Template.CREATE_ISSUE);
//        }
        model.put("createSucceeded", true);
        connectDB.model.createIssue(getSessionCurrentUser(request), getQueryIssueName(request), getQueryIssueDescription(request));
        Issue issue = connectDB.model.getIssueByName(getQueryIssueName(request));
        logger.info("Create issue '{}' with id '{}'", getQueryIssueName(request), issue.getIssue_id());
        response.redirect(Path.Web.ISSUES.concat(issue.getIssue_id()));
        return ViewUtil.render(request, model, Path.Template.CREATE_ISSUE);
    };
}
