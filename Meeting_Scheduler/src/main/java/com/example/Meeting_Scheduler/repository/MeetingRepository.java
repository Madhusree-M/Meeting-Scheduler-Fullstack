package com.example.Meeting_Scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.User;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    // Fetching all meetings creator by a specific user
    List<Meeting> findByCreator(User creator);
}
