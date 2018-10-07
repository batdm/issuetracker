package com.axmor.sql2omodel;

import com.axmor.employee.Employee;
import com.axmor.issue.Issue;
import com.axmor.issue_log.IssueLog;
import com.axmor.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Sql2oModel implements Model {

    final Logger logger = LoggerFactory.getLogger(Sql2oModel.class);
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
    public void createAllTables() {
        try {
            Connection conn;
            SQLTables sqlt = new SQLTables();
            DatabaseMetaData dbm;
            String[] tables = {"EMPLOYEE", "ISSUE", "ISSUE_LOG", "STATUS"};
            for (String t : tables) {
                logger.info("Check table {}", t);
                conn = sql2o.open();
                dbm = conn.getJdbcConnection().getMetaData();
                ResultSet table = dbm.getTables(null, null, t, null);
                if (table.next()) {
                    //table exist
                    logger.info("Table {} already exist.", t);
                } else {
                    //table does not exist
                    logger.info("Table {} does not exist.", t);
                    switch (t) {
                        case "EMPLOYEE":
                            conn.createQuery(sqlt.createEmployee).executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                        case "ISSUE":
                            conn.createQuery(sqlt.createIssue).executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                        case "ISSUE_LOG":
                            conn.createQuery(sqlt.createIssueLog).executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                        case "STATUS":
                            conn.createQuery(sqlt.createStatus).executeUpdate();
                            conn.createQuery(sqlt.insertStatus).executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                    }
                    logger.info("Table {} created", t);
                    conn.commit();
                }
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public List<Issue> getAllIssues() {
        createAllTables();
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
                    .addParameter("issue_name", issue_name)
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
        createAllTables();
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
