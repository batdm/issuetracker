package com.axmor.sql2omodel;

import com.axmor.employee.Employee;
import com.axmor.issue.Issue;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oModel implements Model {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public String createIssue(String issue_name, String author, String description) {
        return null;
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
    public List<Employee> getAllEmployees() {
        try (Connection conn = sql2o.open()) {
            List<Employee> employees = conn.createQuery("select * from employee")
                    .executeAndFetch(Employee.class);
            return employees;
        }
    }

    @Override
    public String getIssue(String issue_id) {
        return null;
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
}
