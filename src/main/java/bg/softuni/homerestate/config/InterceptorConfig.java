package bg.softuni.homerestate.config;

import bg.softuni.homerestate.web.interceptors.TraceAuthorInterceptor;
import bg.softuni.homerestate.web.interceptors.TracePostRequestInterceptor;
import bg.softuni.homerestate.web.interceptors.TraceResponseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final TracePostRequestInterceptor tracePostRequestInterceptor;
    private final TraceResponseInterceptor traceResponseInterceptor;
    private final TraceAuthorInterceptor traceAuthorInterceptor;

    public InterceptorConfig(TracePostRequestInterceptor tracePostRequestInterceptor,
                             TraceResponseInterceptor traceResponseInterceptor, TraceAuthorInterceptor traceAuthorInterceptor) {
        this.tracePostRequestInterceptor = tracePostRequestInterceptor;
        this.traceResponseInterceptor = traceResponseInterceptor;
        this.traceAuthorInterceptor = traceAuthorInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(tracePostRequestInterceptor);
         registry.addInterceptor(traceResponseInterceptor);
         registry.addInterceptor(traceAuthorInterceptor);
    }
}
