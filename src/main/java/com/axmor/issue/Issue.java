package com.axmor.issue;

import lombok.Value;

import java.sql.Date;

@Value // All fields are private and final. Getters (but not setters) are generated (https://projectlombok.org/features/Value.html)
public class Issue {
    String issue_id;
    String employee_id;
    String name;
    String description;
    Date startdate;
    Date lastdate;
    String status;
}
