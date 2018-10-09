package com.axmor.sql2omodel;

import com.axmor.employee.Employee;
import com.axmor.issue.Issue;
import com.axmor.issue_log.IssueLog;
import com.axmor.status.Status;

import java.util.List;

public interface Model {
    void createIssue(String employee_id, String name, String description);

    void createAllTables();

    List<Issue> getAllIssues();

    List<IssueLog> getAllIssueLogs(String issue_name);

    List<Status> getAllStatus();

    List<Employee> getAllEmployees();

    void createEmployee(String login, String password);

    void createComment(String issue_name, String employee_login, String status, String comment);

    Issue getIssueById(String issue_id);

    Issue getIssueByName(String name);

    Employee getEmployeeByLogin(String login);
}
