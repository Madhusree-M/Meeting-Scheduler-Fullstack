package com.example.Meeting_Scheduler.entity;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
// import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String contact;
    private LocalDateTime joinDate = LocalDateTime.now();

    @OneToMany(mappedBy = "creator")
    @JsonManagedReference
    private List<Meeting> meetings;

    // @Transient // This tells Hibernate: "Don't store this in the database"
    // private String joinDateString;

    // public String getJoinDateString() {
    // return joinDateString;
    // }

    // public void setJoinDateString(String joinDateString) {
    // this.joinDateString = joinDateString;
    // }

}
