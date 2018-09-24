package com.axmor.issue;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class IssueDao {
    private final List<Issue> issues = ImmutableList.of(
            new Issue("1", "1", "issue1", "first issue", "01.01.2018", "open"),
            new Issue("2", "2", "issue2", "second issue", "01.02.2018", "resolve")

    );

    public Iterable<Issue> getAllIssues() {
        return issues;
    }

    public Issue getIssueById(String issue_id){
        return issues.stream().filter(b->b.getIssue_id().equals(issue_id)).findFirst().orElse(null);
    }

}
