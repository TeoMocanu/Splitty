package commons;

import commons.emails.Reminder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReminderTest {

    @Test
    public void testParameterizedConstructor() {
        String email = "mario@gmail.com";
        String event = "Shopping";
        double amount = 100.0;
        String debtor = "alex@gmail.com";

        Reminder reminder = new Reminder(email, event, amount, debtor);

        Assertions.assertAll("Constructor should set all properties",
                () -> Assertions.assertEquals(email, reminder.getEmail(), "Email"),
                () -> Assertions.assertEquals(event, reminder.getEvent(), "Event"),
                () -> Assertions.assertEquals(amount, reminder.getAmount(), "Amount"),
                () -> Assertions.assertEquals(debtor, reminder.getDebtor(), "Debtor")
        );
    }

    @Test
    public void testDefaultConstructor() {
        Reminder reminder = new Reminder();

        Assertions.assertAll("Default constructor - properties null",
                () -> Assertions.assertNull(reminder.getEmail(), "Email"),
                () -> Assertions.assertNull(reminder.getEvent(), "Event"),
                () -> Assertions.assertEquals(0.0, reminder.getAmount(), "Amount"),
                () -> Assertions.assertNull(reminder.getDebtor(), "Debtor")
        );
    }

    @Test
    public void testGetEmail() {
        String email = "mario@gmail.com";
        Reminder reminder = new Reminder(email, "Shopping", 100.0, "alex@gmail.com");

        Assertions.assertEquals(email, reminder.getEmail(), "Correct email");
    }

    @Test
    public void testGetEvent() {
        String event = "Shopping";
        Reminder reminder = new Reminder("mario@gmail.com", event, 100.0, "alex@gmail.com");

        Assertions.assertEquals(event, reminder.getEvent(), "Correct event");
    }

    @Test
    public void testGetAmount() {
        double amount = 100.0;
        Reminder reminder = new Reminder("mario@gmail.com", "Shopping", amount, "alex@gmail.com");

        Assertions.assertEquals(amount, reminder.getAmount(), "Correct amount");
    }

    @Test
    public void testGetDebtor() {
        String debtor = "alex@gmail.com";
        Reminder reminder = new Reminder("mario@gmail.com", "Shopping", 100.0, debtor);

        Assertions.assertEquals(debtor, reminder.getDebtor(), "Correct debtor");
    }
}
