// package com.example.Meeting_Scheduler.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.Meeting_Scheduler.entity.Meeting;
// import com.example.Meeting_Scheduler.entity.MeetingSlot;
// import com.example.Meeting_Scheduler.service.MeetingService;
// import com.example.Meeting_Scheduler.service.MeetingSlotService;

// @Controller
// @RequestMapping("/slots")
// public class MeetingSlotController {

//     @Autowired
//     private MeetingSlotService slotService;

//     @Autowired
//     private MeetingService meetingService;

//     // Display all slots
//     @GetMapping("/list")
//     public String getAllSlots(Model model) {
//         model.addAttribute("slots", slotService.getAllMeetingSlots());
//         return "slots/list";
//     }

//     // Form for new slot
//     @GetMapping("/form")
//     public String showSlotForm(Model model) {
//         model.addAttribute("slot", new MeetingSlot());
//         model.addAttribute("meetings", meetingService.getAllMeetings()); // dropdown -> meeting selection
//         return "slots/form";
//     }

//     // Save to db
//     @PostMapping
//     public String saveSlot(@ModelAttribute("slot") MeetingSlot slot, @RequestParam("meetingId") Long meetingId) {

//         // find meeting by id
//         Meeting meeting = meetingService.getMeetingById(meetingId)
//                 .orElseThrow(() -> new RuntimeException("Meeting not found with ID : " + meetingId));
//         slot.setMeeting(meeting);
//         slotService.createSlot(slot);
//         return "redirect:/slots/list";
//     }

//     // Edit -> displays slot form
//     @GetMapping("/edit/{id}")
//     public String editSlotForm(@PathVariable Long id, Model model) {
//         MeetingSlot slot = slotService.getSlotById(id)
//                 .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + id));

//         model.addAttribute("slot", slot);
//         model.addAttribute("meetings", meetingService.getAllMeetings());
//         return "slots/form";
//     }

//     // Update -> save modified slot after editing
//     @PostMapping("/update/{id}")
//     public String updateSlot(@PathVariable Long id, @ModelAttribute("slot") MeetingSlot updatedSlot,
//             @RequestParam("meetingId") Long meetingId) {

//         MeetingSlot existingSlot = slotService.getSlotById(id)
//                 .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + id));

//         Meeting meeting = meetingService.getMeetingById(meetingId)
//                 .orElseThrow(() -> new RuntimeException("Meeting not found with Id : " + meetingId));

//         existingSlot.setMeeting(meeting);
//         existingSlot.setStartTime(updatedSlot.getStartTime());
//         existingSlot.setEndTime(updatedSlot.getEndTime());

//         slotService.createSlot(existingSlot);
//         return "redirect:/slots/list";
//     }

//     // Delete Slot
//     @GetMapping("/delete/{id}")
//     public String deleteSlot(@PathVariable Long id) {
//         slotService.deleteSlot(id);
//         return "redirect:/slots/list";
//     }
// }

// ----------------Postman----------------------

package com.example.Meeting_Scheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.MeetingSlot;
import com.example.Meeting_Scheduler.service.MeetingService;
import com.example.Meeting_Scheduler.service.MeetingSlotService;

@RestController
@RequestMapping("/api/slots")
public class MeetingSlotController {

    @Autowired
    private MeetingSlotService slotService;

    @Autowired
    private MeetingService meetingService;

    // Display all slots
    @GetMapping
    public List<MeetingSlot> getAllSlots(Model model) {
        return slotService.getAllMeetingSlots();
    }

    // Get slot by id
    @GetMapping("/{id}")
    public MeetingSlot getSlotbyId(@PathVariable Long id) {
        return slotService.getSlotById(id).orElseThrow(() -> new RuntimeException("Slot not found with Id : " + id));
    }

    // new slot
    @PostMapping
    public MeetingSlot createSlot(@RequestBody MeetingSlot slot) {
        return slotService.createSlot(slot);
    }

    @PutMapping("/{id}")
    public MeetingSlot updateSlot(@PathVariable Long id, @RequestBody MeetingSlot updatedSlot) {
        MeetingSlot existingSlot = slotService.getSlotById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found with ID: " + id));

        if (updatedSlot.getMeeting() != null && updatedSlot.getMeeting().getMeetingId() != null) {
            Meeting meeting = meetingService.getMeetingById(updatedSlot.getMeeting().getMeetingId())
                    .orElseThrow(() -> new RuntimeException(
                            "Meeting not found with ID: " + updatedSlot.getMeeting().getMeetingId()));
            existingSlot.setMeeting(meeting);
        }

        existingSlot.setStartTime(updatedSlot.getStartTime());
        existingSlot.setEndTime(updatedSlot.getEndTime());

        return slotService.createSlot(existingSlot);
    }

    // Delete a slot
    @DeleteMapping("/{id}")
    public String deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return "Slot deleted successfully";
    }

    // Get all slots for a specific meeting
    @GetMapping("/meeting/{meetingId}")
    public List<MeetingSlot> getSlotsByMeeting(@PathVariable Long meetingId) {
        Meeting meeting = meetingService.getMeetingById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found with ID: " + meetingId));
        return slotService.getSlotsByMeeting(meeting);
    }
}