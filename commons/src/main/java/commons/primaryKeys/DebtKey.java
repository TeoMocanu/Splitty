package commons.primaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class DebtKey implements Serializable{
    @Column(name = "event_id")
    private long eventId;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "debt_id")
    private long id;

    public DebtKey(long eventId) {
        this.eventId = eventId;
    }

    @SuppressWarnings("unused")
    protected DebtKey() {
        // for object mappers
    }

    public long getId() {
        return id;
    }

    public long getEventId() {
        return eventId;
    }
}
