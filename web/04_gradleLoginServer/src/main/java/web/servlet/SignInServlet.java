package web.servlet;

import data.entity.User;
import data.util.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final AccountManager accountManager;

    public SignInServlet() {
        accountManager = AccountManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("exit") != null) {
            System.exit(0);
        }
        resp.getWriter().println("GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userProfile = new User(req.getParameter("login"), req.getParameter("password"));

        System.out.println(userProfile.getLogin());
        System.out.println(userProfile.getPass());

        if (accountManager.checkUserData(userProfile)){
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Authorized: "+userProfile.getLogin());
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Unauthorized");
        }
    }
}
