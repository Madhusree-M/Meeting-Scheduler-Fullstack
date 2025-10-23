package com.example.Meeting_Scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Meeting_Scheduler.entity.MeetingSlot;
import com.example.Meeting_Scheduler.entity.SlotResponse;
import com.example.Meeting_Scheduler.entity.User;

@Repository
public interface SlotResponseRepository extends JpaRepository<SlotResponse, Long> {
    // Finding by slot
    List<SlotResponse> findBySlot(MeetingSlot meetingSlot);

    // Finding by participant
    List<SlotResponse> findByParticipant(User participant);
}
