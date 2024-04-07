package commons.primaryKeys;

import java.io.Serializable;
import java.util.Objects;

public class TagKey implements Serializable {
    private long id;
    private long eventId;

    public TagKey(long eventId, long id) {
        this.id = id;
        this.eventId = eventId;
    }

    @SuppressWarnings("unused")
    protected TagKey() {
        // for object mappers
    }

    public long getId() {
        return id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagKey that = (TagKey) o;
        return id == that.id && eventId == that.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, id);
    }
}
