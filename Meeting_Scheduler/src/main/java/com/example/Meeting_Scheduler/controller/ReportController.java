package com.example.Meeting_Scheduler.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.Meeting_Scheduler.entity.*;
import com.example.Meeting_Scheduler.service.MeetingService;
import com.example.Meeting_Scheduler.service.MeetingSlotService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private MeetingSlotService slotService;

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/meeting-availability/{meetingId}")
    public String getAvailabilityReport(@PathVariable Long meetingId, Model model) {
        Meeting meeting = meetingService.getMeetingById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found with Id : " + meetingId));

        List<MeetingSlot> slots = slotService.getSlotsByMeeting(meeting);
        List<Map<String, Object>> report = new ArrayList<>();

        for (MeetingSlot slot : slots) {
            long availableCount = slot.getSlotResponses().stream()
                    .filter(r -> r.getStatus() == Status.AVAILABLE)
                    .count();

            long unavailableCount = slot.getSlotResponses().stream()
                    .filter(r -> r.getStatus() == Status.UNAVAILABLE)
                    .count();

            Map<String, Object> slotReport = new HashMap<>();
            slotReport.put("slotId", slot.getSlotId());
            slotReport.put("startTime", slot.getStartTime());
            slotReport.put("endTime", slot.getEndTime());
            slotReport.put("availableCount", availableCount);
            slotReport.put("unavailableCount", unavailableCount);

            report.add(slotReport);
        }

        model.addAttribute("meeting", meeting);
        model.addAttribute("report", report);

        return "report/meeting-availability";
    }
}

// ------------------postman------------------

// package com.example.Meeting_Scheduler.controller;

// import java.util.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import com.example.Meeting_Scheduler.entity.*;
// import com.example.Meeting_Scheduler.service.MeetingService;
// import com.example.Meeting_Scheduler.service.MeetingSlotService;

// @RestController
// @RequestMapping("/api/reports")
// public class ReportController {

// @Autowired
// private MeetingSlotService slotService;

// @Autowired
// private MeetingService meetingService;

// @GetMapping("/meeting-availability/{meetingId}")
// public List<Map<String, Object>> getAvailabilityReport(@PathVariable Long
// meetingId) {
// Meeting meeting = meetingService.getMeetingById(meetingId)
// .orElseThrow(() -> new RuntimeException("Meeting not found with Id : " +
// meetingId));
// List<MeetingSlot> slots = slotService.getSlotsByMeeting(meeting);
// List<Map<String, Object>> report = new ArrayList<>();

// for (MeetingSlot slot : slots) {
// long availableCount = slot.getSlotResponses().stream()
// .filter(r -> r.getStatus() == Status.AVAILABLE)
// .count();

// long unavailableCount = slot.getSlotResponses().stream()
// .filter(r -> r.getStatus() == Status.UNAVAILABLE)
// .count();

// Map<String, Object> slotReport = new HashMap<>();
// slotReport.put("slotId", slot.getSlotId());
// slotReport.put("startTime", slot.getStartTime());
// slotReport.put("endTime", slot.getEndTime());
// slotReport.put("availableCount", availableCount);
// slotReport.put("unavailableCount", unavailableCount);

// report.add(slotReport);
// }

// return report;
// }
// }
