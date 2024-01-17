package com.ztcollins.JavaEducator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Entity representing a User in the system.
 * This class models user-related data and is mapped to a database table.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    private String name;
    private boolean adminStatus;
    private String operatingMode;
    private int score;

    /**
     * Gets the unique identifier for the user.
     * 
     * @return the unique identifier of this user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the user.
     * 
     * @param id the new unique identifier for this user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user. The email is unique for each user.
     * 
     * @param email the new email for this user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the name of the user.
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the new name for this user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the admin status of the user.
     * 
     * @return true if the user is an admin, false otherwise
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }

    /**
     * Sets the admin status of the user.
     * 
     * @param adminStatus the new admin status for this user
     */
    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    /**
     * Gets the score of the user.
     * 
     * @return the score of the user
     */
    public int getScore(){
        return score;
    }

    /**
     * Sets the score of the user.
     * 
     * @param score the new score for this user
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Gets the operating mode of the user.
     * 
     * @return the operating mode of the user
     */
    public String getOperatingMode() {
        return operatingMode;
    }

    /**
     * Sets the operating mode of the user.
     * 
     * @param operatingMode the new operating mode for this user
     */
    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
    }

    /**
     * Returns a string representation of the User object.
     * 
     * @return a string representation of the User object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", adminStatus=" + adminStatus +
                ", operatingMode='" + operatingMode + '\'' +
                ", score=" + score +
                '}';
    }
}
