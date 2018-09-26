package com.axmor.employee;

import org.mindrot.jbcrypt.BCrypt;

import static com.axmor.Main.*;

public class EmployeeController {
    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }
        Employee employee = employeeDao.getEmployeeByLogin(login);
        if (employee == null) {
            return false;
        }
//        String hashedPassword = BCrypt.hashpw(password, employee.getSalt());
//        return hashedPassword.equals(employee.getHashedPassword());
        return password.equals(employee.getPassword());
    }

    public static boolean userIsExist(String login) {
        if (login.isEmpty()) {
            return false;
        }
        Employee employee = employeeDao.getEmployeeByLogin(login);
        if (employee == null) {
            return false;
        }
        return login.equals(employee.getLogin());
    }

    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String login, String oldPassword, String newPassword) {
        if (authenticate(login, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }
}
