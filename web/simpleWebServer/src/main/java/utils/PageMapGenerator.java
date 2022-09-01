package utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class PageMapGenerator {

    public static HashMap<String, Object> getVariableMap(HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Message:", request);
        result.put("Method:", request);
        result.put("URL:", request);
        result.put("PathInfo:", request);
        result.put("SessionId:", request);
        result.put("Parameters:", request);

        return result;
    }
}
