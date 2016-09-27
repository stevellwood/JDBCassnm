package org.ssa.tiy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="major")
public class Major {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="description")
    private String description;
    @Column(name="req_sat")
    private int requiredSAT;
    
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRequiredSAT() {
        return requiredSAT;
    }

    public void setRequiredSAT(int requiredSAT) {
        this.requiredSAT = requiredSAT;
    }

    public Major (int id, String description, int requiredSAT) {
        this.id = id;
        this.description = description;
        this.requiredSAT = requiredSAT;
    }
    public Major (String description) {
        
        this.description = description;
        this.requiredSAT = 400;
    }
    
    public Major () {}
    public String toString() {
        return String.format("%3d %-20s %4d", this.id, this.getDescription(), this.getRequiredSAT());
    }
}