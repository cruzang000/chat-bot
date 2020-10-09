package edu.matc.entjava.socialite.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User class defines a User object and its methods
 */
@Entity(name = "User")
@Table(name = "users")
public class User {

    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<UserPlan> userPlans = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Search> searches = new HashSet<>();

    @OneToMany(mappedBy = "requester_user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Friend> requestedFriends = new HashSet<>();

    @OneToMany(mappedBy = "requested_user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Friend> receivedFriends = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<UserPlanInvite> userPlanInvites = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<UserPlanInvite> userRoles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modified;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    /**
     * Instantiates a new User.
     */
    public User() {}

    /**
     * Instantiates a new User.
     *
     * @param username  the username
     * @param password  the password
     * @param lastName  the last name
     * @param firstName the first name
     * @param email     the email
     * @param isAdmin   the is admin
     */
    public User(String username, String password, String lastName, String firstName, String email, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    /**
     * Gets searches.
     *
     * @return the searches
     */
    public Set<Search> getSearches() {
        return searches;
    }

    /**
     * Sets searches.
     *
     * @param searches the searches
     */
    public void setSearches(Set<Search> searches) {
        this.searches = searches;
    }

    /**
     * Gets user plans.
     *
     * @return the user plans
     */
    public Set<UserPlan> getUserPlans() {
        return userPlans;
    }

    /**
     * Sets user plans.
     *
     * @param userPlans the user plans
     */
    public void setUserPlans(Set<UserPlan> userPlans) {
        this.userPlans = userPlans;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
       return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
       this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
       return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
       this.password = password;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
       return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
       this.lastName = lastName;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
       return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
       this.firstName = firstName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
       return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
       this.email = email;
    }

    /**
     * Gets admin.
     *
     * @return the admin
     */
    public Boolean getAdmin() {
       return isAdmin;
    }

    /**
     * Sets admin.
     *
     * @param admin the admin
     */
    public void setAdmin(Boolean admin) {
       isAdmin = admin;
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
     * Gets requested friends.
     *
     * @return the requested friends
     */
    public Set<Friend> getRequestedFriends() {
        return requestedFriends;
    }

    /**
     * Sets requested friends.
     *
     * @param requestedFriends the requested friends
     */
    public void setRequestedFriends(Set<Friend> requestedFriends) {
        this.requestedFriends = requestedFriends;
    }

    /**
     * Gets received friends.
     *
     * @return the received friends
     */
    public Set<Friend> getReceivedFriends() {
        return receivedFriends;
    }

    /**
     * Sets received friends, takes friends set and returns set of accepted request
     *
     * @param receivedFriends the received friends
     */
    public void setReceivedFriends(Set<Friend> receivedFriends) {
        this.receivedFriends = receivedFriends;
    }


    /**
     * takes friend requests set and returns only accepted requests
     * @param friendRequests set of friend requests
     * @param acceptedStatus status to query for
     * @return friends extracted
     */
    private Set<Friend> extractByAcceptedStatus(Set<Friend> friendRequests, String acceptedStatus) {
        switch(acceptedStatus) {
            case "accepted":
                return friendRequests.stream().filter(Friend::getAccepted).collect(Collectors.toSet());
            case "unanswered":
                return friendRequests.stream().filter(request -> (request.getAccepted() == null)).collect(Collectors.toSet());
            default:
                return friendRequests;
        }
    }

    /**
     * Gets friends, function takes receivedFriends and requestedFriends and uses the extract accepted method
     * to extract accepted requests adding to friends set which then gets returned
     *
     * @return the friends
     */
    public Set<Friend> getAcceptedFriends() {

        Set<Friend> friends = new HashSet<>();

        //add accepted from received and requested friends
        friends.addAll(this.extractByAcceptedStatus(this.receivedFriends, "accepted"));
        friends.addAll(this.extractByAcceptedStatus(this.requestedFriends, "accepted"));

        return friends;
    }

    /**
     * get received friend requests that are unanswered using the friends extractor
     *
     * @return friendRequestNotifications friend notifications
     */
    public Set<Friend> getFriendNotifications() {
        //add accepted from received and requested friends
        return new HashSet<>(this.extractByAcceptedStatus(this.receivedFriends, "unanswered"));
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
     * get user plans that have today's date
     *
     * @return current plans
     */
    public Set<UserPlan> getCurrentPlans() {
        return this.userPlans.stream()
                .filter(plan -> plan.getCreated().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()) && !plan.getRemoved())
                .collect(Collectors.toSet());
    }

    /**
     * Gets user roles.
     *
     * @return the user roles
     */
    public Set<UserPlanInvite> getUserRoles() {
        return userRoles;
    }

    /**
     * Sets user roles.
     *
     * @param userRoles the user roles
     */
    public void setUserRoles(Set<UserPlanInvite> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                lastName.equals(user.lastName) &&
                firstName.equals(user.firstName) &&
                email.equals(user.email) &&
                isAdmin.equals(user.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, lastName, firstName, email, isAdmin, id);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", id=" + id +
                '}';
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
}
