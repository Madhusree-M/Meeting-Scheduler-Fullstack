package com.example.Meeting_Scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.MeetingSlot;

@Repository
public interface MeetingSlotRepository extends JpaRepository<MeetingSlot, Long> {
    List<MeetingSlot> findByMeeting(Meeting meeting);
}
