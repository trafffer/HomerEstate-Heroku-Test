package bg.softuni.homerestate.models.binding;

import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.enums.ContactHours;

import javax.validation.constraints.*;


public class InquiryAddBindingModel {
    @Size(max = 20,message = "First name cannot be more than 20 characters!")
    private String firstName;
    @Size(min = 3,max = 20, message = "Last name must be between 3 and 20 characters")
    private String lastName;
    @Pattern(regexp = "^[0-9]*$",message = "The phone number, must contain only digits")
    @Size(min = 7,message = "Phone number must have at least 7 digits")
    private String phoneNumber;
    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotNull(message = "You must select contact hours!")
    private ContactHours contactHour;
    @NotBlank(message = "Message cannot be empty")
    @Size(min = 5,max = 240,message = "Comment length must be between 5 and 240 characters")
    private String description;


    public InquiryAddBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public InquiryAddBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InquiryAddBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InquiryAddBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public InquiryAddBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactHours getContactHour() {
        return contactHour;
    }

    public InquiryAddBindingModel setContactHour(ContactHours contactHour) {
        this.contactHour = contactHour;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InquiryAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
