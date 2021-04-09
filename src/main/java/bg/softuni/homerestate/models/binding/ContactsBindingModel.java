package bg.softuni.homerestate.models.binding;

import javax.validation.constraints.*;

public class ContactsBindingModel {
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @NotNull(message = "Number cannot be empty")
    private Long number;
    @Email
    private String email;
    @NotBlank(message = "You must pick contact time")
    private String contactTime;
    @NotBlank(message = "The subject cannot be empty")
    @Size(min = 3,message = "Subject size must be at least 3 symbols")
    private String subject;
    @NotBlank(message = "Field is required!")
    @Size(min = 5,max = 80,message = "Message length must be between 5 and 80 characters")
    private String message;

    public ContactsBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public ContactsBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ContactsBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Long getNumber() {
        return number;
    }

    public ContactsBindingModel setNumber(Long number) {
        this.number = number;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactsBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContactTime() {
        return contactTime;
    }

    public ContactsBindingModel setContactTime(String contactTime) {
        this.contactTime = contactTime;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ContactsBindingModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactsBindingModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
