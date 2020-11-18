package edu.matc.entjava.socialite.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * friends class defines a friend object and its methods
 */
@Entity(name = "Friend")
@Table(name = "friends")
@Getter
@Setter
public class Friend {

    @ManyToOne
    private User requester_user;

    @ManyToOne
    private User requested_user;

    private Boolean accepted;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modified;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Setter(AccessLevel.PROTECTED) private int id;

    /**
     * Instantiates a new friend.
     */
    public Friend() {
    }

    /**
     * Instantiates a new User friend request.
     *
     * @param requester_user the requester_user
     * @param requested_user the requested_user
     * @param accepted  the accepted
     */
    public Friend(User requester_user, User requested_user, Boolean accepted) {
        this.requester_user = requester_user;
        this.requested_user = requested_user;
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return id == friend.id &&
                requester_user.equals(friend.requester_user) &&
                requested_user.equals(friend.requested_user) &&
                Objects.equals(accepted, friend.accepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requester_user, requested_user, accepted, id);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "requester_user=" + requester_user +
                ", requested_user=" + requested_user +
                ", accepted=" + accepted +
                ", id=" + id +
                '}';
    }
}
