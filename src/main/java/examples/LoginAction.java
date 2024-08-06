package examples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction {

    public static final String ACTION = "path = /login, scope = request, parameter = method";

    private LoginService loginService;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String doLogin() {
        try {
            return loginService.authenticate(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



