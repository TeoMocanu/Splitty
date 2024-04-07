package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import commons.primaryKeys.TagKey;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@IdClass(TagKey.class)
public class Tag {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "tag_id")
    private long id;

    @Id
    @Column(name = "event_id")
    private long eventId;

    @Transient
    private TagKey tagKey;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties({"expenses"})
    private Event event;

    @ManyToMany
    @JsonIgnoreProperties({"event", "tag", "payer", "splitters"})
    @JoinTable(name = "expense_tag",
            joinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"),
                    @JoinColumn(name = "tag_event_id", referencedColumnName = "event_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "expense_id", referencedColumnName = "expense_id"),
                    @JoinColumn(name = "expense_event_id", referencedColumnName = "event_id")})
    List<Expense> expenses;

    private String name;

    protected Tag() {
        // for object mappers
    }

    public Tag(String name, Event event) {
        this.event = event;
        this.eventId = event.getId();
        this.name = name;
        this.tagKey = new TagKey(event.getId(), id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public TagKey getTagKey() {
        return tagKey;
    }

    public void setTagKey(TagKey tagKey) {
        this.tagKey = tagKey;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return name;
    }

}
