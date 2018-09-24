package com.axmor.issue;

import com.axmor.employee.Employee;
import com.axmor.login.LoginController;
import com.axmor.util.Path;
import com.axmor.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static com.axmor.Main.employeeDao;
import static com.axmor.Main.issueDao;
import static com.axmor.util.JsonUtil.*;
import static com.axmor.util.RequestUtil.*;

public class IssueController {
    public static Route fetchAllIssues = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("issues", issueDao.getAllIssues());
            return ViewUtil.render(request, model, Path.Template.ISSUE_ALL);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(issueDao.getAllIssues());
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };
    public static Route fetchOneIssue = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            Issue issue = issueDao.getIssueById(getParamIssueId(request));
            Employee employee = employeeDao.getEmployeeByEmployeeId(issue.getEmployee_id());
            model.put("issue", issue);
            model.put("employee", employee);

            return ViewUtil.render(request, model, Path.Template.ISSUE_ONE);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(issueDao.getIssueById(getParamIssueId(request)));
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };
}
