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
 * location address class defines a location address object and its methods
 */
@Entity(name = "LocationAddress")
@Table(name = "location_addresses")
public class LocationAddress {

    private String street;
    private String city;
    private String state;
    private int zipcode;
    private String latitude;
    private String longitude;
    private Boolean isClosed;

    @ManyToOne
    private Location location;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modified;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets zipcode.
     *
     * @return the zipcode
     */
    public int getZipcode() {
        return zipcode;
    }

    /**
     * Sets zipcode.
     *
     * @param zipcode the zipcode
     */
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets closed.
     *
     * @return the closed
     */
    public Boolean getClosed() {
        return isClosed;
    }

    /**
     * Sets closed.
     *
     * @param closed the closed
     */
    public void setClosed(Boolean closed) {
        isClosed = closed;
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
        LocationAddress that = (LocationAddress) o;
        return zipcode == that.zipcode &&
                id == that.id &&
                street.equals(that.street) &&
                city.equals(that.city) &&
                state.equals(that.state) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                isClosed.equals(that.isClosed) &&
                location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, zipcode, latitude, longitude, isClosed, location, id);
    }

    @Override
    public String toString() {
        return "LocationAddress{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode=" + zipcode +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", isClosed=" + isClosed +
                ", location=" + location +
                ", id=" + id +
                '}';
    }
}
