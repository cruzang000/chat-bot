package edu.matc.entjava.socialite.entity;

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
    private int id;

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

    /**
     * Gets requester_user.
     *
     * @return the requester_user
     */
    public User getrequester_user() {
        return requester_user;
    }

    /**
     * Sets requester_user.
     *
     * @param requester_user the requester_user
     */
    public void setrequester_user(User requester_user) {
        this.requester_user = requester_user;
    }

    /**
     * Gets requested_user.
     *
     * @return the requested_user
     */
    public User getrequested_user() {
        return requested_user;
    }

    /**
     * Sets requested_user.
     *
     * @param requested_user the requested_user
     */
    public void setrequested_user(User requested_user) {
        this.requested_user = requested_user;
    }

    /**
     * Gets accepted.
     *
     * @return the accepted
     */
    public Boolean getAccepted() {
        return accepted;
    }

    /**
     * Sets accepted.
     *
     * @param accepted the accepted
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Gets modified.
     *
     * @return the modified
     */
    public LocalDateTime getModified() {
        return modified;
    }

    /**
     * Sets modified.
     *
     * @param modified the modified
     */
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
