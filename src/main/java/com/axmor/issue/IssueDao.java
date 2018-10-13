package com.axmor.issue;

import java.util.List;

import static com.axmor.Main.connectDB;

public class IssueDao {
    public List<Issue> issues;

    public Iterable<Issue> getAllIssues() {
        issues = connectDB.model.getAllIssues();
        return issues;
    }
}
