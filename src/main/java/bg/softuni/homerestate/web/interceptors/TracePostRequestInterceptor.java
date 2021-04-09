package bg.softuni.homerestate.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TracePostRequestInterceptor implements HandlerInterceptor {
    private final Logger LOGGER = LoggerFactory.getLogger(TracePostRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equalsIgnoreCase("post")) {
            request.setAttribute("startTime", System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getMethod().equalsIgnoreCase("post")) {
            int responseStatus = response.getStatus();
            long currentTime = System.currentTimeMillis();
            String myHeader = request.getRequestURI();
            long testTime = currentTime - (long) request.getAttribute("startTime");
            LOGGER.info(String.format("The request %s took %d ms and the response status is %d", myHeader, testTime, responseStatus));
        }
    }
}
