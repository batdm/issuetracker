package com.axmor.issue_log;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class IssueLog {
    String log_id;
    String issue_name;
    String employee_login;
    String status;
    String comment;
    Timestamp date;

}
