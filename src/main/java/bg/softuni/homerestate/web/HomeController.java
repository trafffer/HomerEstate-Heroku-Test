package bg.softuni.homerestate.web;


import bg.softuni.homerestate.models.binding.SearchBindingModel;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;

@Controller
public class HomeController {
    private final OfferService offerService;


    public HomeController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("offers",offerService.loadViewModels());
        model.addAttribute("categories", Category.values());
        model.addAttribute("cities", City.values());
        model.addAttribute("isHome",true);
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "about-us";
    }

    @GetMapping("/contact")
    public String contacts() {

        return "contacts";
    }

    @GetMapping("/all")
    public String allOffers(Model model){
        model.addAttribute("customSearch",offerService.findAll());
        return "search";
    }

    @ModelAttribute("offerModel")
    public SearchBindingModel createModel(){
        return new SearchBindingModel();
    }

    @GetMapping("/search")
    public String search(@ModelAttribute SearchBindingModel offerModel, Model model){
        if (offerModel.getPriceLow()==null){
            offerModel.setPriceLow(BigDecimal.valueOf(0));
        }
        if (offerModel.getPriceHigh()==null){
            offerModel.setPriceHigh(BigDecimal.valueOf(Integer.MAX_VALUE));
        }
        model.addAttribute("customSearch",offerService.customSearch(offerModel));
        return "search";
    }

}
