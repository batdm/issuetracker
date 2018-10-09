package com.axmor.sql2omodel;

import com.axmor.employee.Employee;
import com.axmor.issue.Issue;
import com.axmor.issue_log.IssueLog;
import com.axmor.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Sql2oModel implements Model {

    private final Logger logger = LoggerFactory.getLogger(Sql2oModel.class);
    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void createIssue(String employee_login, String name, String description) {
        try (Connection conn = sql2o.beginTransaction();
             Query query = conn.createQuery("insert into issue(employee_login,name,description,startdate,lastdate,status) values(:employee_login,:name,:description,sysdate,sysdate,'Open')")
                     .addParameter("employee_login", employee_login)
                     .addParameter("name", name)
                     .addParameter("description", description);
             Connection conn2 = query.executeUpdate()) {
            conn.commit();
        }
    }

    @Override
    public void createAllTables() {
        Connection conn = null;
        Query query = null;
        ResultSet table = null;
        SQLTables sqlt = new SQLTables();
        DatabaseMetaData dbm;
        final String[] tables = {"EMPLOYEE", "ISSUE", "ISSUE_LOG", "STATUS"};
        try {
            for (String t : tables) {
                logger.info("Check table {}", t);
                conn = sql2o.open();
                dbm = conn.getJdbcConnection().getMetaData();
                table = dbm.getTables(null, null, t, null);
                if (table.next()) {
                    //table exist
                    logger.info("Table {} already exist.", t);
                } else {
                    //table does not exist
                    logger.info("Table {} does not exist.", t);
                    switch (t) {
                        case "EMPLOYEE":
//                            conn.createQuery(sqlt.createEmployee).executeUpdate();
                            query = conn.createQuery(sqlt.createEmployee);
                            query.executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                        case "ISSUE":
//                            conn.createQuery(sqlt.createIssue).executeUpdate();
                            query = conn.createQuery(sqlt.createIssue);
                            query.executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                        case "ISSUE_LOG":
//                            conn.createQuery(sqlt.createIssueLog).executeUpdate();
                            query = conn.createQuery(sqlt.createIssueLog);
                            query.executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                        case "STATUS":
//                            conn.createQuery(sqlt.createStatus).executeUpdate();
//                            conn.createQuery(sqlt.insertStatus).executeUpdate();
                            query = conn.createQuery(sqlt.createStatus);
                            query.executeUpdate();
                            query = conn.createQuery(sqlt.insertStatus);
                            query.executeUpdate();
                            logger.info("switch {}, then create query and execute update", t);
                            break;
                    }
                    logger.info("Table {} created", t);
                    conn.commit();
                }
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        } finally {
            if (query != null) {
                query.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (table != null) {
                try {
                    table.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Issue> getAllIssues() {
        createAllTables();
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from issue")) {
            return query.executeAndFetch(Issue.class);
        }
    }

    @Override
    public List<IssueLog> getAllIssueLogs(String issue_name) {
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from issue_log where issue_name = :issue_name ")
                     .addParameter("issue_name", issue_name)) {
            return query.executeAndFetch(IssueLog.class);
        }
    }

    @Override
    public List<Status> getAllStatus() {
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from status")) {
            return query.executeAndFetch(Status.class);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        createAllTables();
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from employee")) {
            return query.executeAndFetch(Employee.class);
        }
    }

    @Override
    public void createEmployee(String login, String password) {
        try (Connection conn = sql2o.beginTransaction();
             Query query = conn.createQuery("insert into employee(login, password) values(:login,:password)")
                     .addParameter("login", login)
                     .addParameter("password", password);
             Connection conn2 = query.executeUpdate()) {
            conn.commit();
        }
    }

    @Override
    public void createComment(String issue_name, String employee_login, String status, String comment) {
        try (Connection conn = sql2o.beginTransaction();
             Query query1 = conn.createQuery("insert into issue_log(issue_name, employee_login,status,comment,date) values(:issue_name, :employee_login,:status,:comment,sysdate)")
                     .addParameter("issue_name", issue_name)
                     .addParameter("employee_login", employee_login)
                     .addParameter("status", status)
                     .addParameter("comment", comment);
             Query query2 = conn.createQuery("update issue set status = :new_status where name = :issue_name")
                     .addParameter("new_status", status)
                     .addParameter("issue_name", issue_name);
             Connection conn1 = query1.executeUpdate();
             Connection conn2 = query2.executeUpdate()) {
            conn.commit();
        }
    }

    @Override
    public Issue getIssueById(String issue_id) {
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from issue where issue_id = :issue_id").addParameter("issue_id", issue_id)) {
            return query.executeAndFetchFirst(Issue.class);
        }
    }

    @Override
    public Issue getIssueByName(String name) {
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from issue where name = :name").addParameter("name", name)) {
            return query.executeAndFetchFirst(Issue.class);
        }
    }

    @Override
    public Employee getEmployeeByLogin(String login) {
        try (Connection conn = sql2o.open();
             Query query = conn.createQuery("select * from employee where login = :login").addParameter("login", login)) {
            return query.executeAndFetchFirst(Employee.class);
        }
    }
}
