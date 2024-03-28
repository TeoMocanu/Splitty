package commons;

public class Invitation {
    private String email;
    private Long code;
    public Invitation(String email, Long code) {
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
}
