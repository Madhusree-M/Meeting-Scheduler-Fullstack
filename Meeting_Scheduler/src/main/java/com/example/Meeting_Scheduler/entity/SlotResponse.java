package com.example.Meeting_Scheduler.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SlotResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    @JsonIgnoreProperties({ "slotResponses", "meeting" })
    private MeetingSlot slot;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    @JsonIgnoreProperties({ "meeting" })
    private User participant;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime respondedAt = LocalDateTime.now();

}
