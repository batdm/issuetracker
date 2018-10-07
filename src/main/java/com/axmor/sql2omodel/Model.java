package com.axmor.sql2omodel;

import java.util.List;

public interface Model {
    void createIssue(String employee_id, String name, String description);
    void createAllTables();
    List getAllIssues();
    List getAllIssueLogs(String issue_name);
    List getAllStatus();
    List getAllEmployees();
    void createEmployee(String login, String password);
    void createComment(String issue_name,String employee_login,String status,String comment);
}
