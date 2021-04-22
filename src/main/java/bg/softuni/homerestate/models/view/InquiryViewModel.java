package bg.softuni.homerestate.models.view;


public class InquiryViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String contactHour;
    private String description;
    private String createdOn;

    public InquiryViewModel() {
    }

    public Long getId() {
        return id;
    }

    public InquiryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public InquiryViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InquiryViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InquiryViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public InquiryViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContactHour() {
        return contactHour;
    }

    public InquiryViewModel setContactHour(String contactHour) {
        this.contactHour = contactHour;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InquiryViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public InquiryViewModel setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }
}
