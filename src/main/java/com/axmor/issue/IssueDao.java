package com.axmor.issue;

import java.util.List;

import static com.axmor.Main.connectDB;

public class IssueDao {
    public List<Issue> issues;

    public Iterable<Issue> getAllIssues() {
        issues = connectDB.model.getAllIssues();
        return issues;
    }

//    public Issue getIssueById(String issue_id) {
//        issues = connectDB.model.getAllIssues();
//        return issues.stream().filter(b -> b.getIssue_id().equals(issue_id)).findFirst().orElse(null);
//    }
//    public Issue getIssueByName(String name) {
//        issues = connectDB.model.getAllIssues();
//        return issues.stream().filter(b -> b.getName().equals(name)).findFirst().orElse(null);
//    }

}
