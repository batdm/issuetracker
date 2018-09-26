package com.axmor.employee;

import java.util.List;
import java.util.stream.Collectors;

import static com.axmor.Main.connectDB;

public class EmployeeDao {
    public List<Employee> employees;
//            = ImmutableList.of(
//            //                       id          login        Salt for hash                    Hashed password (the password is "password" for all users)
////            new Employee("1", "Ivan", "$2a$10$h.dl5J86rGH7I8bD9bZeZe", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO"),
////            new Employee("2", "Petya", "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe", "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy"),
//
//    );

    public Employee getEmployeeByLogin(String login) {
        employees = connectDB.model.getAllEmployees();
        return employees.stream().filter(b -> b.getLogin().equals(login)).findFirst().orElse(null);
    }

    public Iterable<String> getAllLogin() {
        employees = connectDB.model.getAllEmployees();
        return employees.stream().map(Employee::getLogin).collect(Collectors.toList());
    }

    public Employee getEmployeeByEmployeeId(String employee_id) {
        employees = connectDB.model.getAllEmployees();
        return employees.stream().filter(b -> b.getEmployee_id().equals(employee_id)).findFirst().orElse(null);

    }
}
