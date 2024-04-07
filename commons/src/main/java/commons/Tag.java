package commons;

import commons.primaryKeys.TagKey;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

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


}
