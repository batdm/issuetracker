package com.axmor.login;

import com.axmor.employee.EmployeeController;
import com.axmor.util.Path;
import com.axmor.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.axmor.Main.connectDB;
import static com.axmor.util.RequestUtil.*;

public class LoginController {
    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Path.Web.LOGIN);
        }
    }

    public static Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if (!EmployeeController.authenticate(getQueryLogin(request), getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", getQueryLogin(request));
        if (getQueryLoginRedirect(request) != null) {
            response.redirect(getQueryLoginRedirect(request));
        }
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };

    public static Route serveSignUpPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.SIGNUP);
    };

    public static Route handleSignUpPost=(Request request, Response response) ->{
        Map<String, Object> model = new HashMap<>();
        if (EmployeeController.userIsExist(getQueryLogin(request))){
            model.put("loginAlreadyExist", true);
            return ViewUtil.render(request, model, Path.Template.SIGNUP);
        }
        model.put("signupSucceeded", true);
        connectDB.model.createEmployee(getQueryLogin(request),getQueryPassword(request));
        request.session().attribute("createdUser", getQueryLogin(request));
        return ViewUtil.render(request, model, Path.Template.SIGNUP);
    };
}
