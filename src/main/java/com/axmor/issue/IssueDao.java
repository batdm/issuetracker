package com.axmor.issue;

import com.axmor.sql2omodel.*;
import com.google.common.collect.ImmutableList;
import org.sql2o.Sql2o;

import java.util.List;

import static com.axmor.Main.connectDB;

public class IssueDao {
    //    private final List<Issue> issues = ImmutableList.of(
//            new Issue("1", "1", "issue1", "first issue", "01.01.2018", "open"),
//            new Issue("2", "2", "issue2", "second issue", "01.02.2018", "resolve")
//
//    );
//    private Sql2o sql2o = new Sql2o("jdbc:h2:~/test", "admin", "admin");
//    private Sql2oModel model = new Sql2oModel(sql2o);
//    public  List<Issue> issues = model.getAllIssues();
    public List<Issue> issues;

    public Iterable<Issue> getAllIssues() {
        issues = connectDB.model.getAllIssues();
        return issues;
    }

    public Issue getIssueById(String issue_id) {
        issues = connectDB.model.getAllIssues();
        return issues.stream().filter(b -> b.getIssue_id().equals(issue_id)).findFirst().orElse(null);
    }

}
