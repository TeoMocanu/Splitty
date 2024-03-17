package commons.primaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;


public class DebtKey implements Serializable{
    private long eventId;
    private long id;

    public DebtKey(long eventId, long id) {
        this.eventId = eventId;
        this.id = id;
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
        DebtKey debtKey = (DebtKey) o;
        return eventId == debtKey.eventId && id == debtKey.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, id);
    }
}
