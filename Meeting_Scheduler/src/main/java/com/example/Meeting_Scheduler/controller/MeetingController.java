// package com.example.Meeting_Scheduler.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.Meeting_Scheduler.entity.Meeting;
// import com.example.Meeting_Scheduler.service.MeetingService;

// @Controller
// @RequestMapping("/meetings")
// public class MeetingController {

//     @Autowired
//     private MeetingService meetingService;

//     // Display all meetings
//     @GetMapping("/list")
//     public String getAllMeetings(Model model) {
//         model.addAttribute("meetings", meetingService.getAllMeetings());
//         return "meetings/list"; // will call meetings.html
//     }

//     // Form to create new meeting
//     @GetMapping("/form")
//     public String showMeetingForm(Model model) {
//         model.addAttribute("meeting", new Meeting());
//         return "/meetings/form";
//     }

//     // Save new meeting to db
//     @PostMapping("/save")
//     public String createMeeting(@ModelAttribute("meeting") Meeting meeting) {
//         meetingService.createMeeting(meeting);
//         return "redirect:/meetings/list";
//     }

//     // Edit a meeting
//     @GetMapping("/edit/{id}")
//     public String editMeetingForm(@PathVariable Long id, Model model) {
//         Meeting meeting = meetingService.getMeetingById(id)
//                 .orElseThrow(() -> new RuntimeException("Meeting not found with ID : " + id));
//         model.addAttribute("meeting", meeting);
//         return "meetings/form";
//     }

//     // Delete a meeting by id
//     @GetMapping("/delete/{id}")
//     public String deleteMeeting(@PathVariable Long id) {
//         meetingService.deleteMeeting(id);
//         return "redirect:/meetings/list";
//     }
// }

//--------------PostMan---------------
package com.example.Meeting_Scheduler.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Meeting_Scheduler.entity.Meeting;
import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.service.MeetingService;
import com.example.Meeting_Scheduler.service.UserService;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    // Display all meetings
    @GetMapping
    public List<Meeting> getAllMeetings() {
        return meetingService.getAllMeetings(); // will call meetings.html
    }

    // Get a meeting by Id
    @GetMapping("/{id}")
    public Optional<Meeting> showMeetingForm(@PathVariable Long id) {
        return meetingService.getMeetingById(id);
    }

    // Create new meeting
    @PostMapping
    public Meeting createMeeting(@RequestBody Meeting meeting) {

        // base condition : checking for user existence
        if (meeting.getCreator() != null && meeting.getCreator().getUserId() != null) {
            User creator = userService.getUserById(meeting.getCreator().getUserId())
                    .orElseThrow(
                            () -> new RuntimeException("User not found with ID: " + meeting.getCreator().getUserId()));
            meeting.setCreator(creator);
        }
        return meetingService.createMeeting(meeting);
    }

    // Edit a meeting
    @PutMapping("/{id}")
    public Meeting updateMeeting(@PathVariable Long id, @RequestBody Meeting meetingDetails) {

        Meeting meeting = meetingService.getMeetingById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with Id : " + id));

        // Base Condition
        if (meetingDetails.getCreator() != null && meetingDetails.getCreator().getUserId() != null) {
            User creator = userService.getUserById(meetingDetails.getCreator().getUserId())
                    .orElseThrow(() -> new RuntimeException(
                            "User not found with Id : " + meetingDetails.getCreator().getUserId()));
            meeting.setCreator(creator);
        }
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setMeetingSlots(meetingDetails.getMeetingSlots());
        meeting.setFinalizedSlot(meetingDetails.getFinalizedSlot());
        meeting.setDescription(meetingDetails.getDescription());
        return meetingService.createMeeting(meeting);

    }

    // Delete a meeting by id
    @DeleteMapping("/{id}")
    public String deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return "Meeting deleted successfully";
    }
}
