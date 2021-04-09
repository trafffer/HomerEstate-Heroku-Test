package bg.softuni.homerestate.web;

import bg.softuni.homerestate.services.CategoryService;
import bg.softuni.homerestate.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/add")
    public String add(Model model){
        if (!model.containsAttribute("model")) {
            model.addAttribute("noUser", false);
            model.addAttribute("noRole", false);
        }
        model.addAttribute("names", userService.findAllUsernames());
        return "role-config";
    }

    @PostMapping("/add")
    public String addConfirm(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String role, Model model,
                             RedirectAttributes redirectAttributes){
        if (username.isBlank()||role==null){
            if (username.isBlank()) {
                redirectAttributes.addFlashAttribute("model", model);
                redirectAttributes.addFlashAttribute("noUser", true);
            }
            if (role==null){
                redirectAttributes.addFlashAttribute("model", model);
                redirectAttributes.addFlashAttribute("noRole",true);
            }
            return "redirect:/roles/add";
        }
        userService.changeRole(username, role);
        return "redirect:/";
    }
}
