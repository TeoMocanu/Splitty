package commons.primaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseKey that = (ExpenseKey) o;
        return eventId == that.eventId && id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, id);
    }
}
