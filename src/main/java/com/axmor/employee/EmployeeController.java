package com.axmor.employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.axmor.Main.*;

public class EmployeeController {
    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }
        Employee employee = connectDB.model.getEmployeeByLogin(login);
        if (employee == null) {
            return false;
        }
        return password.equals(employee.getPassword());
    }

    public static boolean userIsExist(String login) {//Check existing login
        if (login.isEmpty()) {
            return false;
        }
        Employee employee = connectDB.model.getEmployeeByLogin(login);
        if (employee == null) {
            return false;
        }
        return login.equals(employee.getLogin());
    }

    public static boolean isIncorrectLogin(String login) {//Verification string on contains spec chars. return false when login contains a-z or 0-9
        if (login == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(login);
        return matcher.find();
    }
}
