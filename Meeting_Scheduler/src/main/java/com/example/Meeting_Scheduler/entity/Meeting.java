package com.example.Meeting_Scheduler.entity;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonIgnoreProperties("meetings")
    private User creator;

    private String title;
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime finalizedSlot;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MeetingSlot> meetingSlots;
}
