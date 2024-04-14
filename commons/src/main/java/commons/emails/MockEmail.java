package commons.emails;

public class MockEmail {
    private String event;
    public MockEmail(String event) {
        this.event = event;
    }

    public MockEmail() {
    }
    public String getEvent() {
        return event;
    }
}
