package com.axmor.employee;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDao {
    private final List<Employee> employees = ImmutableList.of(
            //                       id          login        Salt for hash                    Hashed password (the password is "password" for all users)
            new Employee("1", "Ivan", "$2a$10$h.dl5J86rGH7I8bD9bZeZe", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO"),
            new Employee("2", "Petya", "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe", "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy"),
            new Employee("3", "Vasya", "$2a$10$E3DgchtVry3qlYlzJCsyxe", "$2a$10$E3DgchtVry3qlYlzJCsyxeSK0fftK4v0ynetVCuDdxGVl1obL.ln2")
    );

    public Employee getEmployeeByLogin(String login) {
        return employees.stream().filter(b -> b.getLogin().equals(login)).findFirst().orElse(null);
    }

    public Iterable<String> getAllLogin() {
        return employees.stream().map(Employee::getLogin).collect(Collectors.toList());
    }

    public Employee getEmployeeByEmployeeId(String employee_id) {
        return employees.stream().filter(b -> b.getEmployee_id().equals(employee_id)).findFirst().orElse(null);

//        for (Employee empl : employees) {
//            if (employee_id.equals(empl.getEmployee_id())) {
//                return empl.getLogin();
//            }
//            return null;
//        }
//        return null;
    }
}
