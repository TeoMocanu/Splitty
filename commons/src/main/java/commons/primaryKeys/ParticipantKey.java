package commons.primaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ParticipantKey implements Serializable {
    @Column(name = "event_id")
    private long eventId;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participant_id")
    private long id;

    public ParticipantKey(long eventId) {
        this.eventId = eventId;
    }

    @SuppressWarnings("unused")
    protected ParticipantKey() {
        // for object mappers
    }

    public long getEventId() {
        return eventId;
    }

    public long getId() {
        return id;
    }
}
