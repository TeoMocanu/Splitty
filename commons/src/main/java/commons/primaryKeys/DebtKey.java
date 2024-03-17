package commons.primaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DebtKey implements Serializable{
    @Column(name = "event_id")
    private long eventId;

    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebtKey debtKey = (DebtKey) o;
        return eventId == debtKey.eventId && id == debtKey.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, id);
    }
}
