package bg.softuni.homerestate.web.interceptors;

import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.repositories.OfferRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TraceAuthorInterceptor implements HandlerInterceptor {
    private final OfferRepository repository;

    public TraceAuthorInterceptor( OfferRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().contains("/offers/edit/")) {
            int index = request.getRequestURI().lastIndexOf('/');
            int id = Integer.parseInt(request.getRequestURI().substring(index+1));
            long indexOffer = Long.parseLong(String.valueOf(id));
            Offer offer = repository.findById(indexOffer).get();
            String authorUsername = offer.getAuthor().getUsername();
            String currentUserName = request.getUserPrincipal().getName();
            if (!authorUsername.equals(currentUserName)) {
                response.sendError(403,"You are not authorized to edit this!");
            }
        }
        return true;
    }

}

