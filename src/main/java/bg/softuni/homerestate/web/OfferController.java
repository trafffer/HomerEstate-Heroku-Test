package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.OfferAddBindingModel;
import bg.softuni.homerestate.models.binding.OfferEditBindingModel;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.service.OfferServiceModel;
import bg.softuni.homerestate.services.CloudinaryService;
import bg.softuni.homerestate.services.OfferService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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
        model.addAttribute("noType",false);
        return new OfferAddBindingModel();
    }

    @GetMapping("/add")
    public String addOffer(Model model){
        offerSetUp(model);
        return "add-offer";
    }

    @PostMapping("/add")
    public String addOfferConfirm(@RequestParam("files") List<MultipartFile> files,
                                  @Valid @ModelAttribute("offerModel") OfferAddBindingModel offerModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile optionalFile = files.stream().findFirst().get();
        if (optionalFile.getSize()!=0){
            List<String> images = getCollect(files);
            offerModel.setImgUrl(images);
        } else {
            redirectAttributes.addFlashAttribute("noPictures", true);
        }
        if (offerModel.getType()==null){
            redirectAttributes.addFlashAttribute("noType",true);
        }
        if (bindingResult.hasErrors()||optionalFile.getSize()==0){
            redirectAttributes.addFlashAttribute("offerModel",offerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel",bindingResult);
            return "redirect:/offers/add";
        }
        OfferServiceModel serviceModel = mapper.map(offerModel,OfferServiceModel.class);
        offerService.saveOffer(serviceModel);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editOffer(@PathVariable("id") Long id, Model model){
        model.addAttribute("categories", Category.values());
        model.addAttribute("cities", City.values());
        model.addAttribute("viewModel",offerService.getOffer(id));
        return "edit-offer";
    }

    @ModelAttribute("offerEditModel")
    public OfferEditBindingModel editModel(){
        return new OfferEditBindingModel();
    }

    @PostMapping("/edit/{id}")
    public String editOfferConfirm(@PathVariable("id") Long id,
                                   @RequestParam("files") List<MultipartFile> files,
                                   @Valid @ModelAttribute("offerEditModel") OfferEditBindingModel offerModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes)  {
        MultipartFile optionalFile = files.stream().findFirst().get();
        if (optionalFile.getSize()!=0){
            System.out.println("there are pictures");
            List<String> images = getCollect(files);
            offerModel.setImgUrl(images);
        }
        OfferAddBindingModel offerView = mapper.map(offerService.getOffer(id), OfferAddBindingModel.class);
        if (offerModel.getDescription().length()==0){
            offerModel.setDescription(null);
        }
        if (offerModel.getAddress().length()==0){
            offerModel.setAddress(null);
        }
        if (bindingResult.hasErrors()){
            System.out.println("binding result has errors");
            redirectAttributes.addFlashAttribute("offerEditModel",offerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerEditModel",bindingResult);
            return "redirect:/offers/edit/{id}";
        }
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.map(offerModel,offerView);
        OfferServiceModel serviceModel = mapper.map(offerView,OfferServiceModel.class);
        offerService.editOffer(serviceModel,id);
        return "redirect:/";
    }

    private List<String> getCollect(@RequestParam("files") List<MultipartFile> files) {
        return files.stream().map(f -> {
            try {
                imgUrl = cloudinaryService.uploadImage(f);
            } catch (IOException e) {
                throw new NullPointerException("No file selected!");
            }
            return imgUrl;
        }).collect(Collectors.toList());
    }

    private void offerSetUp(Model model) {
        if (!model.containsAttribute("offerModel")){
            model.addAttribute("offerModel", new OfferAddBindingModel());
            model.addAttribute("noPictures",false);
            model.addAttribute("noType",false);
        }
        model.addAttribute("categories", Category.values());
        model.addAttribute("cities", City.values());
    }
}
