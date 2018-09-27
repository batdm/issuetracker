package com.axmor.issue_log;

import java.util.List;
import static com.axmor.Main.connectDB;

public class IssueLogDao {
    public List<IssueLog> issue_logs;
    public Iterable<IssueLog> getAllIssueLogs(String issue_name) {
        issue_logs = connectDB.model.getAllIssueLogs(issue_name);
        return issue_logs;
    }

    public int issueLogSize() {
        return issue_logs.size();
    }
}
