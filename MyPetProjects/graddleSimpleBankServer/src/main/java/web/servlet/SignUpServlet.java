package web.servlet;

import data.entities.Person;
import web.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private final AccountService accountService;

    public SignUpServlet() {
        accountService = AccountService.instance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* Person person = new Person(req.getParameter("login"), req.getParameter("password"));


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
        }*/
    }
}
