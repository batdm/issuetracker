package com.axmor.issue;

import lombok.Value;

import java.sql.Timestamp;

@Value
// All fields are private and final. Getters (but not setters) are generated (https://projectlombok.org/features/Value.html)
public class Issue {
    String issue_id;
    String employee_login;
    String name;
    String description;
    Timestamp startdate;
    Timestamp lastdate;
    String status;
}
