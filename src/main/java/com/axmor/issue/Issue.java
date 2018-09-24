package com.axmor.issue;

import lombok.Value;

@Value // All fields are private and final. Getters (but not setters) are generated (https://projectlombok.org/features/Value.html)
public class Issue {
    String issue_id;
    String employee_id;
    String name;
    String description;
    String startdate;
    String status;
}
