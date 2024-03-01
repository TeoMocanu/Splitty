package commons.primary_keys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ParticipantKey implements Serializable {
    @Id
    @JoinColumn(name = "event_id")
    private long eventId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
