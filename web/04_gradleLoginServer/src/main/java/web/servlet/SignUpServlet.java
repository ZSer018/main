package web.servlet;

import data.entity.User;
import data.util.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private final AccountManager accountManager;

    public SignUpServlet() {
        accountManager = AccountManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(req.getParameter("login"), req.getParameter("password"));


       // accountManager.addNewUser(user);

       if (accountManager.isRegistred(user)){
              resp.setStatus(HttpServletResponse.SC_CONFLICT);
              resp.setContentType("text/html;charset=utf-8");
              resp.getWriter().println("Такой пользователь уже зарегистрирован");
        } else {
              accountManager.addNewUser(user);
              resp.setStatus(HttpServletResponse.SC_OK);
              resp.setContentType("text/html;charset=utf-8");
              resp.getWriter().println("Пользователь успешно зарегистрирован");
        }
    }
}
