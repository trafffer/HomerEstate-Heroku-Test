package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.UserRegisterBindingModel;
import bg.softuni.homerestate.models.service.UserServiceModel;
import bg.softuni.homerestate.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userModel",userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel",bindingResult);
            return "redirect:/users/register";
        }
        if (userService.userExist(userModel.getUsername())){
           redirectAttributes.addFlashAttribute("userModel",userModel);
           redirectAttributes.addFlashAttribute("userExist", true);
           return "redirect:/users/register";
        }
        UserServiceModel userServiceModel = mapper.map(userModel,UserServiceModel.class);
        userService.registerAndLogin(userServiceModel);
        return "redirect:/";
    }





}
