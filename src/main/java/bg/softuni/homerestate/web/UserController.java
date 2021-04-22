package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.UserRegisterBindingModel;
import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.service.UserServiceModel;
import bg.softuni.homerestate.models.view.UserViewModel;
import bg.softuni.homerestate.services.HomerDBUserService;
import bg.softuni.homerestate.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;


    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login-error")
    public ModelAndView failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                            String username,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("bad_credentials",true);
        redirectAttributes.addFlashAttribute("username",username);
        modelAndView.setViewName("redirect:/users/login");
        return modelAndView;
    }

    @GetMapping("/register")
    public String register(Model model){
        if (!model.containsAttribute("userModel")){
            model.addAttribute("userModel",new UserRegisterBindingModel());
            model.addAttribute("userExist",false);
            model.addAttribute("emailExist",false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        validateUsernameAndEmail(userModel, redirectAttributes);
        if (bindingResult.hasErrors()||redirectAttributes.getFlashAttributes().containsValue(true)){
            redirectAttributes.addFlashAttribute("userModel",userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel",bindingResult);
            return "redirect:/users/register";
        }
        UserServiceModel userServiceModel = mapper.map(userModel,UserServiceModel.class);
        userService.registerAndLogin(userServiceModel);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editProfile(Model model){
        UserViewModel userModel = userService.loadViewModel();
        if (!model.containsAttribute("userBindingModel")){
            model.addAttribute("userBindingModel",new UserRegisterBindingModel());
            model.addAttribute("userExist",false);
            model.addAttribute("emailExist",false);
        }
        model.addAttribute("userViewModel",userModel);
        return "edit-profile";
    }

    @PostMapping("/edit")
    public String editConfirm(@Valid UserRegisterBindingModel userModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws Exception {

        validateUsernameAndEmail(userModel, redirectAttributes);
        UserViewModel loggedUser = userService.loadViewModel();
        if (userModel.getEmail().equals(loggedUser.getEmail())){
            redirectAttributes.addFlashAttribute("emailExist", false);
        }
        if (userModel.getUsername().equals(loggedUser.getUsername())){
            redirectAttributes.addFlashAttribute("userExist", false);
        }
        if (bindingResult.hasErrors()||redirectAttributes.getFlashAttributes().containsValue(true)){
            redirectAttributes.addFlashAttribute("userBindingModel",userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userBindingModel",bindingResult);
            return "redirect:/users/edit";
        }
        UserServiceModel userServiceModel = mapper.map(userModel,UserServiceModel.class);
        userService.editUser(userServiceModel);
        return "redirect:/";
    }

    private void validateUsernameAndEmail(@Valid UserRegisterBindingModel userModel, RedirectAttributes redirectAttributes) {
        if (userService.userExist(userModel.getUsername())){
            redirectAttributes.addFlashAttribute("userExist", true);
        }else{
            redirectAttributes.addFlashAttribute("userExist", false);
        }
        if (userService.emailExist(userModel.getEmail())){
            redirectAttributes.addFlashAttribute("emailExist", true);
        }else{
            redirectAttributes.addFlashAttribute("emailExist", false);
        }
    }
}
