package commons.primaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ExpenseKey implements Serializable{
    @Column(name = "event_id")
    private long eventId;

    @GeneratedValue(strategy = GenerationType.AUTO)
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
