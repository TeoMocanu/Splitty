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
    private Event event;

    @Column(name = "participant_name", nullable = false)
    private String name;
    @Column(name = "participant_email", nullable = false)
    private String email;
    @Column(name = "participant_iban")
    private String iban;
    @Column(name = "participant_bic")
    private String bic;

    public Participant(){ }

    public Participant(long eventId, String name, String email, String iban, String bic){
        this.participantKey = new ParticipantKey(eventId);
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }

    public long getId() {
        return participantKey.getId();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
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
