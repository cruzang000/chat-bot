package edu.matc.entjava.socialite.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User plan class defines a User plans object and its methods
 */
@Entity(name = "UserPlan")
@Table(name = "user_plans")
public class UserPlan {

    private Boolean removed;

    @ManyToOne
    private User user;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "user_plan", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    private Set<UserPlanInvite> userPlanInvites = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modified;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    /**
     * Instantiates a new User plan.
     */
    public UserPlan() {}

    /**
     * Instantiates a new User plan.
     *
     * @param removed         the removed
     * @param user            the user
     * @param location        the location
     */
    public UserPlan(Boolean removed, User user, Location location) {
        this.removed = removed;
        this.user = user;
        this.location = location;
    }

    /**
     * Gets user plan invites.
     *
     * @return the user plan invites
     */
    public Set<UserPlanInvite> getUserPlanInvites() {
        return userPlanInvites;
    }

    /**
     * Sets user plan invites.
     *
     * @param userPlanInvites the user plan invites
     */
    public void setUserPlanInvites(Set<UserPlanInvite> userPlanInvites) {
        this.userPlanInvites = userPlanInvites;
    }

    /**
     * Gets removed.
     *
     * @return the removed
     */
    public Boolean getRemoved() {
        return removed;
    }

    /**
     * Sets removed.
     *
     * @param removed the removed
     */
    public void setRemoved(Boolean removed) {
        this.removed = removed;
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
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
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
        UserPlan userPlan = (UserPlan) o;
        return id == userPlan.id &&
                removed.equals(userPlan.removed) &&
                user.equals(userPlan.user) &&
                location.equals(userPlan.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(removed, user, location, id);
    }

    @Override
    public String toString() {
        return "UserPlan{" +
                "removed=" + removed +
                ", user=" + user +
                ", location=" + location +
                ", id=" + id +
                '}';
    }
}
