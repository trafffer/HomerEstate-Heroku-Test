package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.view.OfferViewModel;
import bg.softuni.homerestate.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class SearchController {
    private final OfferService offerService;

    public SearchController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/api")
    private ResponseEntity<List<OfferViewModel>> findAll(){
       return ResponseEntity.ok()
               .body(offerService.loadViewModels());
    }
}
