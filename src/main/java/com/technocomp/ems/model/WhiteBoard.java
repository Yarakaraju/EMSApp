package com.technocomp.ems.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "white_board")
public class WhiteBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Column(name = "email")
    private String email;

    @Column(name = "item_title")
    @NotEmpty(message = "*Please provide Item title")
    private String itemTitle;

    @Column(name = "item_description")
    @NotEmpty(message = "*Please provide Item description")
    private String itemDescription;

    @Column(name = "total_enroled")
    private int totalEnrolled;

    @Column(name = "max_pratispents")
    @Range(min = 1, message = "minimum should be one and max is 100")
    private int maxPratispents;

    @Column(name = "user_mobile")
    @NotEmpty(message = "*Please enter mobile number")
    private String mobile;

    @Column(name = "location")
    @NotEmpty(message = "*Please enter your event location")
    private String location;

    @Column(name = "latitude")
    @NotEmpty(message = "*Please allow your browser to get latitude")
    private String latitude;

    @Column(name = "longitude")
    @NotEmpty(message = "*Please allow your browser to get longitude")
    private String longitude;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        String formatted = String.format("%.5f", Double.parseDouble(latitude));
        System.out.println(" Formatted latitude is :" + formatted);
        this.latitude = formatted;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        String formatted = String.format("%.5f", Double.parseDouble(longitude));
        System.out.println(" Formatted longitude is :" + formatted);
        this.longitude = formatted;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getTotalEnrolled() {
        return totalEnrolled;
    }

    public void setTotalEnrolled(int totalEnrolled) {
        this.totalEnrolled = totalEnrolled;
    }

    public int getMaxPratispents() {
        return maxPratispents;
    }

    public void setMaxPratispents(int maxPratispents) {
        this.maxPratispents = maxPratispents;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
