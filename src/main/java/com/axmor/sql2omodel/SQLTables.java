package com.axmor.sql2omodel;

public class SQLTables {
    public String createEmployee = "create table employee(\n" +
            "employee_id int primary key auto_increment,\n" +
            "login varchar(255),\n" +
            "password varchar(255));";
    public String createIssue = "create table issue(\n" +
            "issue_id int primary key auto_increment,\n" +
            "employee_login varchar(255),\n" +
            "name varchar(255),\n" +
            "description varchar(255),\n" +
            "startdate timestamp,\n" +
            "lastdate timestamp,\n" +
            "status varchar(255),\n" +
            "foreign key(employee_login) references employee(login));";
    public String createStatus = "create table status(\n" +
            "status_id int primary key auto_increment,\n" +
            "name varchar(255));" +
            "insert into status (name) values ('open'),('close'),('resolve');";
    public String createIssueLog = "create table issue_log(\n" +
            "log_id int primary key auto_increment,\n" +
            "issue_name varchar(255),\n" +
            "employee_login varchar(255),\n" +
            "status varchar(255),\n" +
            "comment varchar(255),\n" +
            "date timestamp,\n" +
            "foreign key(issue_name) references issue(name),\n" +
            "foreign key(employee_login) references employee(login))";
}
