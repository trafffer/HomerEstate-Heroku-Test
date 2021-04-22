package bg.softuni.homerestate.init;

import bg.softuni.homerestate.services.CategoryService;
import bg.softuni.homerestate.services.RoleService;
import bg.softuni.homerestate.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HomerDBApplicationInit implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final CategoryService categoryService;

    public HomerDBApplicationInit(UserService userService, RoleService roleService, CategoryService categoryService) {
        this.userService = userService;
        this.roleService = roleService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
        categoryService.initCategories();
        userService.initUsers();
    }
}
