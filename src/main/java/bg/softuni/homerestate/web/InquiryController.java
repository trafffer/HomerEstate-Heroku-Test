package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.InquiryAddBindingModel;
import bg.softuni.homerestate.models.service.InquiryServiceModel;
import bg.softuni.homerestate.models.view.OfferViewModel;
import bg.softuni.homerestate.services.InquiryService;
import bg.softuni.homerestate.services.OfferService;
import bg.softuni.homerestate.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/inquiries")
public class InquiryController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final InquiryService inquiryService;


    public InquiryController(OfferService offerService, ModelMapper modelMapper, InquiryService inquiryService) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.inquiryService = inquiryService;

    }

    @GetMapping("/add")
    public String addInquiry(Model model){
        model.addAttribute("offers",offerService.getOfferByAuthor());
        return "inquiries";
    }

    @PostMapping("/add/{id}")
    public String addInquiryConfirm(@PathVariable("id") Long id,
                                    @Valid InquiryAddBindingModel inquiryModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("inquiryModel",inquiryModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inquiryModel",bindingResult);
            redirectAttributes.addFlashAttribute("offerModel",id);
            return "redirect:/comments/search/{id}";
        }
        InquiryServiceModel serviceModel = modelMapper.map(inquiryModel,InquiryServiceModel.class);
        inquiryService.saveInquiry(serviceModel,id);
        return "redirect:/";
    }
}
