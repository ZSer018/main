package servlet;

import utils.PageGenerator;
import utils.PageMapGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class MirrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = PageMapGenerator.getVariableMap(req);
        pageVariables.put("mirrorMessage", "");


        Map<String, String[]> test = req.getParameterMap();
        test.forEach((s, strings) -> {

                Arrays.asList(strings).forEach(s1 -> {
                    try {
                        resp.getWriter().println(s1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        });

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = PageMapGenerator.getVariableMap(req);

        String message = req.getParameter("mirrorMessage");

        resp.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("mirrorMessage", message == null ? "" : message);

        resp.getWriter().println(PageGenerator.getInstance().getVariablePage("mirrorpage.html", pageVariables));
    }
}
