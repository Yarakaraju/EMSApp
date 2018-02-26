/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technocomp.ems.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Ravi Varma Yarakaraj
 */
@Entity
@Table(name="notices")
public class Notices  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;
    
    @Column(name = "latitudefornotice")
    @NotEmpty(message = "*Please select latitude/ longitude on map or eneter manually")
    private String latitudefornotice;

    @Column(name = "longitudefornotice")
    @NotEmpty(message = "*Please select latitude/ longitude on map or eneter manually")
    private String longitudefornotice;
    
    @Column(name = "maxRadius")
    @Range(min=1,max = 20, message = "minimum is one mile and max is 10 miles")
    private int maxRadius;

    public String getLatitudefornotice() {
        return latitudefornotice;
    }

    public void setLatitudefornotice(String latitudefornotice) {
        this.latitudefornotice = latitudefornotice;
    }

    public String getLongitudefornotice() {
        return longitudefornotice;
    }

    public void setLongitudefornotice(String longitudefornotice) {
        this.longitudefornotice = longitudefornotice;
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    
}
