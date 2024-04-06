package commons.emails;

public class Invitation {
    private String email;
    private String event;
    private Long code;
    public Invitation(String email, String event, Long code) {
        this.event = event;
        this.email = email;
        this.code = code;
    }

    public Invitation() {
    }

    public String getEmail() {
        return email;
    }

    public Long getCode() {
        return code;
    }

    public String getEvent() {
        return event;
    }
}
