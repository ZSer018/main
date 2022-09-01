package servlet;

import utils.PageGenerator;
import utils.PageMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        HashMap<String, Object> responseMap = PageMapGenerator.getVariableMap(req);
        responseMap.put("lalala", "");

        resp.getWriter().println(PageGenerator.getInstance().getVariablePage("index.html", responseMap));

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        HashMap<String, Object> responseMap = PageMapGenerator.getVariableMap(req);
        String message = req.getParameter("lalala");
        resp.setContentType("text/html;charset=utf-8");


        if (message == null || message.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        responseMap.put("Message:", message == null ? "" : message);

        resp.getWriter().println(PageGenerator.getInstance().getVariablePage("index.html", responseMap));
    }
}
