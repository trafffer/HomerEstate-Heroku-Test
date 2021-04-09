package bg.softuni.homerestate.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TraceResponseInterceptor implements HandlerInterceptor {
    private final Logger LOGGER = LoggerFactory.getLogger(TraceResponseInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        String uri = request.getRequestURI();
        if (response.getStatus()==404 && !uri.equalsIgnoreCase("/error") ){
            LOGGER.info(String.format("Wrong request URI: %s",uri));
        }
    }
}
