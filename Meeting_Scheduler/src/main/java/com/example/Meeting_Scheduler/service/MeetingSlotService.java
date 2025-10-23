package com.example.Meeting_Scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.MeetingSlot;
import com.example.Meeting_Scheduler.repository.MeetingSlotRepository;

@Service
public class MeetingSlotService {
    private MeetingSlotRepository slotRepository;

    public MeetingSlotService(MeetingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    // Create new slot
    public MeetingSlot createSlot(MeetingSlot slot) {
        return slotRepository.save(slot);
    }

    // Get all slots
    public List<MeetingSlot> getAllMeetingSlots() {
        return slotRepository.findAll();
    }

    // Get meeting slot by Id
    public Optional<MeetingSlot> getSlotById(Long id) {
        return slotRepository.findById(id);
    }

    // Get all slots that are alloted for a specific meeting
    public List<MeetingSlot> getSlotsByMeeting(Meeting meeting) {
        return slotRepository.findByMeeting(meeting);
    }

    // Delete a slot
    public void deleteSlot(Long id) {
        slotRepository.deleteById(id);
    }
}
