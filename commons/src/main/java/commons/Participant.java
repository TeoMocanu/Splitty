package commons;

import commons.primary_keys.ParticipantKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Participant {
    @EmbeddedId
    private ParticipantKey participantKey;

    @ManyToOne
    @MapsId("event_id")
    @JoinColumn(name = "event_id")
    public Event event;

    public String name;
    public String email;
    public String iban;

    private String bic;

    public Participant(){ }

    public Participant(long eventId, long id){
        this.participantKey = new ParticipantKey(eventId, id);
    }

    public Participant(long eventId, long id, String email, String iban){
        this.participantKey = new ParticipantKey(eventId, id);
        this.email = email;
        this.iban = iban;
    }

    public ParticipantKey getParticipantKey() {
        return participantKey;
    }

    public void setParticipantKey(ParticipantKey participantKey) {
        this.participantKey = participantKey;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
