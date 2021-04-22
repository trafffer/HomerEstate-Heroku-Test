package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.CommentAddBindingModel;
import bg.softuni.homerestate.models.binding.InquiryAddBindingModel;
import bg.softuni.homerestate.models.entities.enums.ContactHours;
import bg.softuni.homerestate.models.service.CommentServiceModel;
import bg.softuni.homerestate.services.CommentService;
import bg.softuni.homerestate.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final OfferService offerService;
    private final ModelMapper mapper;

    public CommentController(CommentService commentService, OfferService offerService, ModelMapper mapper) {
        this.commentService = commentService;
        this.offerService = offerService;
        this.mapper = mapper;
    }

    @GetMapping("/search/{id}")
    public String offers(@PathVariable("id")Long id, Model model){
        if (!model.containsAttribute("comment")){
            model.addAttribute("comment",new CommentAddBindingModel());
        }
        if (!model.containsAttribute("inquiryModel")){
            model.addAttribute("inquiryModel", new InquiryAddBindingModel());
        }
        model.addAttribute("contactHours", ContactHours.values());
        model.addAttribute("commentsList",commentService.findAllByOfferId(id));
        model.addAttribute("offerModel", offerService.getOffer(id));
        return "details-offer";
    }

    @PostMapping("/search/{id}")
    public String offerSearch(@PathVariable("id") Long id, @Valid CommentAddBindingModel comment,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("comment",comment);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comment",bindingResult);
            return "redirect:/comments/search/{id}";
        }
        CommentServiceModel commentServiceModel = mapper.map(comment,CommentServiceModel.class);
        commentService.saveComment(commentServiceModel,id);
        return "redirect:/comments/search/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable("id") Long id){
        commentService.deleteById(id);
        return "redirect:/";
    }


}
