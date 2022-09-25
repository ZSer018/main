package servlet;

import utils.AccountManager;
import utils.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SingInServlet extends HttpServlet {

    private AccountManager accountManager;

    public SingInServlet(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfile userProfile = new UserProfile(req.getParameter("login"), req.getParameter("password"), "mail");

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
