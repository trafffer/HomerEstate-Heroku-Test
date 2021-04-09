package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.OfferAddBindingModel;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.service.OfferServiceModel;
import bg.softuni.homerestate.services.CloudinaryService;
import bg.softuni.homerestate.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final ModelMapper mapper;
    private final OfferService offerService;
    private final CloudinaryService cloudinaryService;
    private String imgUrl;


    public OfferController(ModelMapper mapper, OfferService offerService, CloudinaryService cloudinaryService) {
        this.mapper = mapper;
        this.offerService = offerService;
        this.cloudinaryService = cloudinaryService;
    }

    @ModelAttribute("offerModel")
    public OfferAddBindingModel model(Model model){
        model.addAttribute("noPictures",false);
    return new OfferAddBindingModel();
    }


    @GetMapping("/add")
    public String addOffer(Model model){
        if (!model.containsAttribute("offerModel")) {
            model.addAttribute("offerModel", new OfferAddBindingModel());
            model.addAttribute("noPictures",false);
            model.addAttribute("noType",false);
        }
            model.addAttribute("categories", Category.values());
            model.addAttribute("cities", City.values());
        return "add-offer";
    }

    @PostMapping("/add")
    public String addOfferConfirm(@RequestParam("files") List<MultipartFile> files,
                                  @Valid @ModelAttribute("offerModel") OfferAddBindingModel offerModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile optionalFile = files.stream().findFirst().get();
        if (optionalFile.getSize()!=0){
            List<String> images = files.stream().map(f -> {
                try {
                    System.out.println("Try to fill pictures");
                    imgUrl = cloudinaryService.uploadImage(f);
                } catch (IOException e) {
                    throw new NullPointerException("No file selected!");
                }
                return imgUrl;
            }).collect(Collectors.toList());
            offerModel.setImgUrl(images);
            System.out.println("File is full");
        } else {
            redirectAttributes.addFlashAttribute("noPictures", true);
            System.out.println("File is empty");
        }
        System.out.println(offerModel.getImgUrl().size());
        if (offerModel.getType()==null){
            redirectAttributes.addFlashAttribute("noType",true);
        }
        if (bindingResult.hasErrors()||optionalFile.getSize()==0){
            System.out.println("binding result has errors");
         redirectAttributes.addFlashAttribute("offerModel",offerModel);
         redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel",bindingResult);
         return "redirect:/offers/add";
     }
        System.out.println("size:"+offerModel.getImgUrl().size());
        OfferServiceModel serviceModel = mapper.map(offerModel,OfferServiceModel.class);
        offerService.saveOffer(serviceModel);
        return "redirect:/";
    }
}
