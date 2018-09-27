package com.axmor.sql2omodel;

import com.axmor.employee.Employee;
import com.axmor.issue.Issue;
import com.axmor.issue_log.IssueLog;
import com.axmor.status.Status;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oModel implements Model {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void createIssue(String employee_login, String name, String description) {
        try (
                Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("insert into issue(employee_login,name,description,startdate,lastdate,status) values(:employee_login,:name,:description,sysdate,sysdate,'Open')")
                    .addParameter("employee_login", employee_login)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .executeUpdate();
            conn.commit();
        }
    }


    @Override
    public String addComment(String text) {
        return null;
    }

    @Override
    public List<Issue> getAllIssues() {
        try (Connection conn = sql2o.open()) {
            List<Issue> issues = conn.createQuery("select * from issue")
                    .executeAndFetch(Issue.class);
            return issues;
        }
    }

    @Override
    public List<IssueLog> getAllIssueLogs(String issue_name) {
        try (Connection conn = sql2o.open()) {
            List<IssueLog> issue_logs = conn.createQuery("select * from issue_log where issue_name = :issue_name ")
                    .addParameter("issue_name",issue_name)
                    .executeAndFetch(IssueLog.class);
            return issue_logs;
        }
    }

    @Override
    public List<Status> getAllStatus() {
        try (Connection conn = sql2o.open()) {
            List<Status> statuses = conn.createQuery("select * from status")
                    .executeAndFetch(Status.class);
            return statuses;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try (Connection conn = sql2o.open()) {
            List<Employee> employees = conn.createQuery("select * from employee")
                    .executeAndFetch(Employee.class);
            return employees;
        }
    }

    @Override
    public void createEmployee(String login, String password) {
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("insert into employee(login, password) values(:login,:password)")
                    .addParameter("login", login)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();
        }
    }

    @Override
    public void createComment(String issue_name, String employee_login, String status, String comment) {
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("insert into issue_log(issue_name, employee_login,status,comment,date) values(:issue_name, :employee_login,:status,:comment,sysdate)")
                    .addParameter("issue_name", issue_name)
                    .addParameter("employee_login", employee_login)
                    .addParameter("status", status)
                    .addParameter("comment", comment)
                    .executeUpdate();
            conn.commit();
        }
    }
}
