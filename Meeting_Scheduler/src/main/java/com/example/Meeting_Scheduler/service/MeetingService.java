package com.example.Meeting_Scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.repository.MeetingRepository;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    // Create a meeting
    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    // Get all Meeting
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    // Find Meeting by Id
    public Optional<Meeting> getMeetingById(Long id) {
        return meetingRepository.findById(id);
    }

    // Get all meetings created by specific creator
    public List<Meeting> getMeetingsByCreator(User creator) {
        return meetingRepository.findByCreator(creator);
    }

    // Delete a meeting
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
