package bg.softuni.homerestate.models.binding;

import bg.softuni.homerestate.models.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldMatch(first = "password",second = "confirmPassword")
public class UserRegisterBindingModel {
    @NotBlank
    @Size(min = 3,max = 20,message = "The username must be between 3 and 20 characters")
    private String username;
    @NotBlank
    @Size(min = 3,max = 20,message = "The fullname must be between 3 and 20 characters")
    private String fullName;
    @NotBlank
    @Size(min = 5,max = 20,message = "The password must be between 5 and 20 characters")
    private String password;
    @NotBlank(message = "This cannot be blank")
    private String confirmPassword;
    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegisterBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
