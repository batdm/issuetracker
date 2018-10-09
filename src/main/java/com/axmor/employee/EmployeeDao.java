package com.axmor.employee;

import java.util.List;
import java.util.stream.Collectors;

import static com.axmor.Main.connectDB;

public class EmployeeDao {
    public List<Employee> employees;

//    public Employee getEmployeeByLogin(String login) {
//        employees = connectDB.model.getAllEmployees();
//        return employees.stream().filter(b -> b.getLogin().equals(login)).findFirst().orElse(null);
//    }

}
