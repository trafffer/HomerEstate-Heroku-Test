package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SaleController {
    private final OfferService offerService;

    public SaleController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    public String allSales(Model model){
        model.addAttribute("sales",offerService.findAllByType(Type.SALE));
        return "sale";
    }
}
