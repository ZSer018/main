package socket;

import org.eclipse.jetty.websocket.servlet.*;

import javax.servlet.annotation.WebServlet;

@WebServlet
public class ChatWebSocketServlet extends WebSocketServlet{

    private final long LOGOUT_TIME = 10 * 60 * 1000;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new EchoSocket());
    }
}
