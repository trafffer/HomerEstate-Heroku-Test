package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rents")
public class RentController {
    private final OfferService offerService;

    public RentController(OfferService offerService) {
        this.offerService = offerService;
    }


    @GetMapping("/all")
    public String allRents(Model model){
        model.addAttribute("rents",offerService.findAllByType(Type.RENT));
        return "rent";
    }
}
