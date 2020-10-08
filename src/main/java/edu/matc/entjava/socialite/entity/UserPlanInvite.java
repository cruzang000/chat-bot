package edu.matc.entjava.socialite.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User plan invite class defines a User plan object and its methods
 */
@Entity(name = "UserPlanInvite")
@Table(name = "user_plan_invites")
public class UserPlanInvite {

    private Boolean closed;
    private Boolean attending;
    private String declineMessage;

    @ManyToOne
    private User user;
    @ManyToOne
    private UserPlan user_plan;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modified;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    /**
     * Instantiates a new User plan invite.
     */
    public UserPlanInvite() {
    }

    /**
     * Instantiates a new User plan invite.
     *
     * @param closed         the closed
     * @param attending      the attending
     * @param declineMessage the decline message
     * @param user           the user
     * @param user_plan       the user plan
     */
    public UserPlanInvite(Boolean closed, Boolean attending, String declineMessage, User user, UserPlan user_plan) {
        this.closed = closed;
        this.attending = attending;
        this.declineMessage = declineMessage;
        this.user = user;
        this.user_plan = user_plan;
    }

    /**
     * Gets closed.
     *
     * @return the closed
     */
    public Boolean getClosed() {
        return closed;
    }

    /**
     * Sets closed.
     *
     * @param closed the closed
     */
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    /**
     * Gets attending.
     *
     * @return the attending
     */
    public Boolean getAttending() {
        return attending;
    }

    /**
     * Sets attending.
     *
     * @param attending the attending
     */
    public void setAttending(Boolean attending) {
        this.attending = attending;
    }

    /**
     * Gets decline message.
     *
     * @return the decline message
     */
    public String getDeclineMessage() {
        return declineMessage;
    }

    /**
     * Sets decline message.
     *
     * @param declineMessage the decline message
     */
    public void setDeclineMessage(String declineMessage) {
        this.declineMessage = declineMessage;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets user plan.
     *
     * @return the user plan
     */
    public UserPlan getUserPlan() {
        return user_plan;
    }

    /**
     * Sets user plan.
     *
     * @param user_plan the user plan
     */
    public void setUserPlan(UserPlan user_plan) {
        this.user_plan = user_plan;
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
        UserPlanInvite that = (UserPlanInvite) o;
        return id == that.id &&
                closed.equals(that.closed) &&
                attending.equals(that.attending) &&
                Objects.equals(declineMessage, that.declineMessage) &&
                user.equals(that.user) &&
                user_plan.equals(that.user_plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(closed, attending, declineMessage, user, user_plan, id);
    }

    @Override
    public String toString() {
        return "UserPlanInvite{" +
                "closed=" + closed +
                ", attending=" + attending +
                ", declineMessage='" + declineMessage + '\'' +
                ", user=" + user +
                ", user_plan=" + user_plan +
                ", id=" + id +
                '}';
    }
}
