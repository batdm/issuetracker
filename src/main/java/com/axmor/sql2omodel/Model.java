package com.axmor.sql2omodel;

import java.util.List;

public interface Model {
    String createIssue(String issue_name, String author, String description);
    String addComment(String text);
    List getAllIssues();
    List getAllEmployees();
    String getIssue(String issue_id);
    void createEmployee(String login, String password);
}
