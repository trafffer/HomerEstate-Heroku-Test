package bg.softuni.homerestate.models.view;


public class CommentViewModel {
    private Long id;
    private String authorUsername;
    private String createdOn;
    private String textContent;
    private Long offerId;

    public CommentViewModel() {
    }

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public CommentViewModel setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public CommentViewModel setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentViewModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }


    public Long getOfferId() {
        return offerId;
    }

    public CommentViewModel setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }
}
