package utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class PageMapGenerator {

    public static HashMap<String, Object> getVariableMap(HttpServletRequest request){

        HashMap<String, Object> result = new HashMap<>();

        result.put("method", request.getMethod());
        result.put("URL", request.getRequestURL().toString());
        result.put("pathInfo", request.getPathInfo());
        result.put("sessionId", request.getSession().getId());
        result.put("parameters", request.getParameterMap().toString());

        return result;
    }
}
