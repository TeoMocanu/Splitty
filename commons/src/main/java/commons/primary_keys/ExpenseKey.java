package commons.primary_keys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ExpenseKey implements Serializable{
    @Id
    @JoinColumn(name = "event_id")
    private long eventId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "expense_id")
    private long id;

    public ExpenseKey(long eventId) {
        this.eventId = eventId;
    }

    @SuppressWarnings("unused")
    protected ExpenseKey() {
        // for object mappers
    }

    public long getEventId() {
        return eventId;
    }

    public long getId() {
        return id;
    }
}
