package com.example.Meeting_Scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Meeting_Scheduler.entity.MeetingSlot;
import com.example.Meeting_Scheduler.entity.SlotResponse;
import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.repository.SlotResponseRepository;

@Service
public class SlotResponseService {
    private final SlotResponseRepository responseRepository;

    public SlotResponseService(SlotResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    // create a response
    public SlotResponse createResponse(SlotResponse response) {
        return responseRepository.save(response);
    }

    // Get all responses
    public List<SlotResponse> getAllResponses() {
        return responseRepository.findAll();
    }

    // Get response by Id
    public Optional<SlotResponse> getResponseById(Long id) {
        return responseRepository.findById(id);
    }

    // Get all responses for a specific slot
    public List<SlotResponse> getResponsesBySlot(MeetingSlot slot) {
        return responseRepository.findBySlot(slot);
    }

    // Get all responses by a specific participant
    public List<SlotResponse> getSlotsByParticipants(User participant) {
        return responseRepository.findByParticipant(participant);
    }

    // Delete a response
    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }
}
