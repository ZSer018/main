package servlet;

import utils.PageGenerator;
import utils.PageMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class SimpleServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = PageMapGenerator.getVariableMap(request);
        pageVariables.put("indexMessage", "");

        response.getWriter().println(PageGenerator.getInstance().getVariablePage("index.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = PageMapGenerator.getVariableMap(request);

        String message = request.getParameter("indexMessage");

        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        pageVariables.put("indexMessage", message == null ? "" : message);

        response.getWriter().println(PageGenerator.getInstance().getVariablePage("index.html", pageVariables));
    }


}