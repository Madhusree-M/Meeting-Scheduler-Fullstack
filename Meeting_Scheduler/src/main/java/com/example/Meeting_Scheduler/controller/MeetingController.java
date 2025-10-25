package com.example.Meeting_Scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.MeetingSlot;
import com.example.Meeting_Scheduler.entity.SlotResponse;
import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.service.MeetingService;
import com.example.Meeting_Scheduler.service.MeetingSlotService;
import com.example.Meeting_Scheduler.service.SlotResponseService;
import com.example.Meeting_Scheduler.service.UserService;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingSlotService slotService;

    @Autowired
    private SlotResponseService responseService;

    // Display all meetings
    @GetMapping("/list")
    public String getAllMeetings(Model model) {
        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "meetings/list"; // will call meetings.html
    }

    // Form to create new meeting
    @GetMapping("/form")
    public String showMeetingForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("users", userService.getAllUsers());
        return "/meetings/form";
    }

    // Show form to add slots for a meeting
    @GetMapping("/{id}/slots/form")
    public String showAddSlotForm(@PathVariable Long id, Model model) {
        Meeting meeting = meetingService.getMeetingById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with ID: " + id));
        model.addAttribute("meeting", meeting);
        model.addAttribute("slot", new MeetingSlot());
        return "meetings/slot-form"; // new Thymeleaf template
    }

    // Save slot for a meeting
    @PostMapping("/{id}/slots/save")
    public String addSlotToMeeting(@PathVariable Long id, @ModelAttribute("slot") MeetingSlot slot) {
        Meeting meeting = meetingService.getMeetingById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with ID: " + id));
        slot.setMeeting(meeting);
        slotService.createSlot(slot);
        return "redirect:/meetings/list";
    }

    // Save new meeting to db
    @PostMapping("/save")
    public String createMeeting(@ModelAttribute("meeting") Meeting meeting) {
        if (meeting.getCreator() != null && meeting.getCreator().getUserId() != null) {
            User creator = userService.getUserById(meeting.getCreator().getUserId()).orElseThrow(
                    () -> new RuntimeException("User not found with Id : " + meeting.getCreator().getUserId()));
            meeting.setCreator(creator);
        }
        meetingService.createMeeting(meeting);
        return "redirect:/meetings/list";
    }

    // Edit a meeting
    @GetMapping("/edit/{id}")
    public String editMeetingForm(@PathVariable Long id, Model model) {
        Meeting meeting = meetingService.getMeetingById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with ID : " + id));
        model.addAttribute("meeting", meeting);
        model.addAttribute("users", userService.getAllUsers()); // for dropdown
        return "meetings/form";
    }

    // Update existing meeting
    @PostMapping("/update/{id}")
    public String updateMeeting(@PathVariable Long id, @ModelAttribute("meeting") Meeting meetingDetails) {
        Meeting meeting = meetingService.getMeetingById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with ID : " + id));

        // Update fields
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setDescription(meetingDetails.getDescription());

        if (meetingDetails.getCreator() != null && meetingDetails.getCreator().getUserId() != null) {
            User creator = userService.getUserById(meetingDetails.getCreator().getUserId())
                    .orElseThrow(() -> new RuntimeException(
                            "User not found with ID : " + meetingDetails.getCreator().getUserId()));
            meeting.setCreator(creator);
        }

        meetingService.createMeeting(meeting);
        return "redirect:/meetings/list";
    }

    // Show availability form for a slot
    @GetMapping("/slots/{slotId}/responses/form")
    public String showSubmitAvailabilityForm(@PathVariable Long slotId, Model model) {
        MeetingSlot slot = slotService.getSlotById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with ID: " + slotId));
        model.addAttribute("slot", slot);
        model.addAttribute("response", new SlotResponse());
        model.addAttribute("users", userService.getAllUsers());
        return "responses/form"; // new Thymeleaf template
    }

    // Save availability response
    @PostMapping("/slots/{slotId}/responses/save")
    public String submitAvailability(@PathVariable Long slotId, @ModelAttribute("response") SlotResponse response) {
        MeetingSlot slot = slotService.getSlotById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with ID: " + slotId));

        if (response.getParticipant() != null && response.getParticipant().getUserId() != null) {
            User participant = userService.getUserById(response.getParticipant().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            response.setParticipant(participant);
        }

        response.setSlot(slot);
        responseService.createResponse(response);
        return "redirect:/meetings/list";
    }

    // Show finalize slot form
    @GetMapping("/{meetingId}/finalize/form")
    public String showFinalizeForm(@PathVariable Long meetingId, Model model) {
        Meeting meeting = meetingService.getMeetingById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        model.addAttribute("meeting", meeting);
        return "meetings/finalize-form"; // new Thymeleaf template
    }

    // Save finalized slot
    @PostMapping("/{meetingId}/finalize/save")
    public String finalizeMeeting(@PathVariable Long meetingId, @RequestParam("slotId") Long slotId) {
        Meeting meeting = meetingService.getMeetingById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        MeetingSlot slot = slotService.getSlotById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        meeting.setFinalizedSlot(slot.getStartTime());
        meetingService.createMeeting(meeting);
        return "redirect:/meetings/list";
    }

    // Delete a meeting by id
    @GetMapping("/delete/{id}")
    public String deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return "redirect:/meetings/list";
    }
}

// --------------PostMan---------------
// package com.example.Meeting_Scheduler.controller;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.Meeting_Scheduler.entity.Meeting;
// import com.example.Meeting_Scheduler.entity.MeetingSlot;
// import com.example.Meeting_Scheduler.entity.SlotResponse;
// import com.example.Meeting_Scheduler.entity.User;
// import com.example.Meeting_Scheduler.service.MeetingService;
// import com.example.Meeting_Scheduler.service.MeetingSlotService;
// import com.example.Meeting_Scheduler.service.SlotResponseService;
// import com.example.Meeting_Scheduler.service.UserService;

// @RestController
// @RequestMapping("/api/meetings")
// public class MeetingController {

// @Autowired
// private MeetingService meetingService;

// @Autowired
// private UserService userService;

// @Autowired
// private MeetingSlotService slotService;

// @Autowired
// private SlotResponseService responseService;

// // Display all meetings
// @GetMapping
// public List<Meeting> getAllMeetings() {
// return meetingService.getAllMeetings(); // will call meetings.html
// }

// // Get a meeting by Id
// @GetMapping("/{id}")
// public Optional<Meeting> showMeetingForm(@PathVariable Long id) {
// return meetingService.getMeetingById(id);
// }

// // Create new meeting
// @PostMapping
// public Meeting createMeeting(@RequestBody Meeting meeting) {

// // base condition : checking for user existence
// if (meeting.getCreator() != null && meeting.getCreator().getUserId() != null)
// {
// User creator = userService.getUserById(meeting.getCreator().getUserId())
// .orElseThrow(
// () -> new RuntimeException("User not found with ID: " +
// meeting.getCreator().getUserId()));
// meeting.setCreator(creator);
// }
// return meetingService.createMeeting(meeting);
// }

// // Add slots for a meeting
// @PostMapping("/{meetingId}/slots")
// public MeetingSlot addSlotToMeeting(@PathVariable Long meetingId,
// @RequestBody MeetingSlot slot) {
// Meeting meeting = meetingService.getMeetingById(meetingId)
// .orElseThrow(() -> new RuntimeException("Meeting not found with ID: " +
// meetingId));

// slot.setMeeting(meeting);
// return slotService.createSlot(slot);
// }

// // Edit a meeting
// @PutMapping("/{id}")
// public Meeting updateMeeting(@PathVariable Long id, @RequestBody Meeting
// meetingDetails) {

// Meeting meeting = meetingService.getMeetingById(id)
// .orElseThrow(() -> new RuntimeException("Meeting not found with Id : " +
// id));

// // Base Condition
// if (meetingDetails.getCreator() != null &&
// meetingDetails.getCreator().getUserId() != null) {
// User creator =
// userService.getUserById(meetingDetails.getCreator().getUserId())
// .orElseThrow(() -> new RuntimeException(
// "User not found with Id : " + meetingDetails.getCreator().getUserId()));
// meeting.setCreator(creator);
// }
// meeting.setTitle(meetingDetails.getTitle());
// meeting.setMeetingSlots(meetingDetails.getMeetingSlots());
// meeting.setFinalizedSlot(meetingDetails.getFinalizedSlot());
// meeting.setDescription(meetingDetails.getDescription());
// return meetingService.createMeeting(meeting);

// }

// // finalize a slot
// @PutMapping("/{meetingId}/finalize")
// public Meeting finalizeMeeting(@PathVariable Long meetingId, @RequestBody
// Map<String, Long> body) {
// Meeting meeting = meetingService.getMeetingById(meetingId)
// .orElseThrow(() -> new RuntimeException("Meeting not found"));

// Long slotId = body.get("slotId");
// MeetingSlot slot = slotService.getSlotById(slotId)
// .orElseThrow(() -> new RuntimeException("Slot not found with ID: " +
// slotId));

// meeting.setFinalizedSlot(slot.getStartTime());
// return meetingService.createMeeting(meeting);
// }

// // Submit Availability for a slot
// @PostMapping("/{meetingId}/slots/{slotId}/responses")
// public SlotResponse submitAvailability(
// @PathVariable Long meetingId,
// @PathVariable Long slotId,
// @RequestBody SlotResponse response) {

// MeetingSlot slot = slotService.getSlotById(slotId)
// .orElseThrow(() -> new RuntimeException("Slot not found with ID: " +
// slotId));

// if (response.getParticipant() == null ||
// response.getParticipant().getUserId() == null) {
// throw new RuntimeException("Participant is required");
// }

// User participant =
// userService.getUserById(response.getParticipant().getUserId())
// .orElseThrow(() -> new RuntimeException("User not found"));

// response.setSlot(slot);
// response.setParticipant(participant);

// return responseService.createResponse(response);
// }

// // Delete a meeting by id
// @DeleteMapping("/{id}")
// public String deleteMeeting(@PathVariable Long id) {
// meetingService.deleteMeeting(id);
// return "Meeting deleted successfully";
// }
// }
