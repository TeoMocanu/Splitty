package commons.emails;

public class Reminder {
    private String email;
    private String event;
    private double amount;
    private String debtor;
    public Reminder(String email, String event, double amount, String debtor) {
        this.event = event;
        this.email = email;
        this.amount = amount;
        this.debtor = debtor;
    }

    public Reminder() {
    }

    public String getEmail() {
        return email;
    }

    public String getEvent() {
        return event;
    }

    public double getAmount() {
        return amount;
    }

    public String getDebtor() {
        return debtor;
    }
}
