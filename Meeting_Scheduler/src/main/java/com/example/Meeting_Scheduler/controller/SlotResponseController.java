// package com.example.Meeting_Scheduler.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.Meeting_Scheduler.entity.MeetingSlot;
// import com.example.Meeting_Scheduler.entity.SlotResponse;
// import com.example.Meeting_Scheduler.entity.User;
// import com.example.Meeting_Scheduler.service.MeetingSlotService;
// import com.example.Meeting_Scheduler.service.SlotResponseService;
// import com.example.Meeting_Scheduler.service.UserService;

// @Controller
// @RequestMapping("/responses")
// public class SlotResponseController {

//     @Autowired
//     private SlotResponseService responseService;

//     @Autowired
//     private MeetingSlotService slotService;

//     @Autowired
//     private UserService userService;

//     // Display all Responses
//     @GetMapping("/list")
//     public String getAllResponses(Model model) {
//         model.addAttribute("responses", responseService.getAllResponses());
//         return "/responses/list";
//     }

//     // Form for new response
//     @GetMapping("/form")
//     public String showResponseForm(Model model) {
//         model.addAttribute("response", new SlotResponse());
//         model.addAttribute("slots", slotService.getAllMeetingSlots());
//         model.addAttribute("users", userService.getAllUsers());
//         return "/responses/form";
//     }

//     // Save response to db
//     @PostMapping
//     public String createResponse(@ModelAttribute("response") SlotResponse response,
//             @RequestParam("slotId") Long slotId, @RequestParam("participantId") Long participantId) {
//         MeetingSlot slot = slotService.getSlotById(slotId)
//                 .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + slotId));

//         User participant = userService.getUserById(participantId)
//                 .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + slotId));

//         response.setSlot(slot);
//         response.setParticipant(participant);
//         responseService.createResponse(response);

//         return "redirect:/responses/list";
//     }

//     // Edit response form
//     @GetMapping("/edit/{id}")
//     public String editResponseForm(@PathVariable Long id, Model model) {
//         SlotResponse response = responseService.getResponseById(id)
//                 .orElseThrow(() -> new RuntimeException("Response not found with Id : " + id));
//         model.addAttribute("response", response);
//         model.addAttribute("slot", slotService.getAllMeetingSlots());
//         return "responses/form";
//     }

//     @PostMapping("/update/{id}")
//     public String updateResponse(@PathVariable Long id, @ModelAttribute("response") SlotResponse updatedResponse,
//             @RequestParam("slotId") Long slotId, @RequestParam("participantId") Long participantId) {
//         SlotResponse existingResponse = responseService.getResponseById(id)
//                 .orElseThrow(() -> new RuntimeException("Response not found with Id : " + id));

//         MeetingSlot slot = slotService.getSlotById(slotId)
//                 .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + slotId));

//         User participant = userService.getUserById(participantId)
//                 .orElseThrow(() -> new RuntimeException("User not found with Id : " + participantId));
//         existingResponse.setSlot(slot);
//         existingResponse.setParticipant(participant);
//         existingResponse.setStatus(updatedResponse.getStatus());

//         responseService.createResponse(existingResponse);
//         return "redirect:/responses/list";
//     }

//     // Delete response
//     @GetMapping("/delete/{id}")
//     public String deleteResponse(@PathVariable Long id) {
//         responseService.deleteResponse(id);
//         return "redirect:/responses/list";
//     }
// }

//--------------------PostMan----------------------

package com.example.Meeting_Scheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Meeting_Scheduler.entity.MeetingSlot;
import com.example.Meeting_Scheduler.entity.SlotResponse;
import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.service.MeetingSlotService;
import com.example.Meeting_Scheduler.service.SlotResponseService;
import com.example.Meeting_Scheduler.service.UserService;

@RestController
@RequestMapping("/api/responses")
public class SlotResponseController {

    @Autowired
    private SlotResponseService responseService;

    @Autowired
    private MeetingSlotService slotService;

    @Autowired
    private UserService userService;

    // Display all responses
    @GetMapping
    public List<SlotResponse> getAllResponses() {
        return responseService.getAllResponses();
    }

    // Get response by Id
    @GetMapping("/{id}")
    public SlotResponse getResponseById(@PathVariable Long id) {
        return responseService.getResponseById(id)
                .orElseThrow(() -> new RuntimeException("Response not found with Id : " + id));
    }

    // create new response
    @PostMapping
    public SlotResponse createResponse(@RequestBody SlotResponse response) {
        // Base condition -> validating slot
        if (response.getSlot() == null || response.getSlot().getSlotId() == null) {
            throw new RuntimeException("Slot is required");
        }

        MeetingSlot slot = slotService.getSlotById(response.getSlot().getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + response.getSlot().getSlotId()));

        // Base condition -> Validating participant
        if (response.getParticipant() == null || response.getParticipant().getUserId() == null) {
            throw new RuntimeException("Participant is required");
        }

        User participant = userService.getUserById(response.getParticipant().getUserId()).orElseThrow(
                () -> new RuntimeException("User not found with Id : " + response.getParticipant().getUserId()));

        response.setSlot(slot);
        response.setParticipant(participant);

        return responseService.createResponse(response);
    }

    // Update existing response
    @PutMapping("/{id}")
    public SlotResponse updateResponse(@PathVariable Long id, @RequestBody SlotResponse updatedResponse) {
        SlotResponse existingResponse = responseService.getResponseById(id)
                .orElseThrow(() -> new RuntimeException("Response not found with Id : " + id));

        // base condition -> Updating slot if exists
        if (updatedResponse.getSlot() != null && updatedResponse.getSlot().getSlotId() != null) {
            MeetingSlot slot = slotService.getSlotById(updatedResponse.getSlot().getSlotId()).orElseThrow(
                    () -> new RuntimeException("Slot not found with Id : " + updatedResponse.getSlot().getSlotId()));
            existingResponse.setSlot(slot);
        }

        // base condition -> Updating participant if exists
        if (updatedResponse.getParticipant() != null && updatedResponse.getParticipant().getUserId() != null) {
            User participant = userService.getUserById(updatedResponse.getParticipant().getUserId())
                    .orElseThrow(() -> new RuntimeException(
                            "Participant not found with Id : " + updatedResponse.getParticipant().getUserId()));
            existingResponse.setParticipant(participant);
        }

        existingResponse.setStatus(updatedResponse.getStatus());
        return responseService.createResponse(existingResponse);
    }

    // delete response
    @DeleteMapping("/{id}")
    public String deleteResponse(@PathVariable Long id) {
        responseService.deleteResponse(id);
        return "SlotResponse deleted successfully";
    }

    // Get all responses for a particular slot
    @GetMapping("/slot/{slotId}")
    public List<SlotResponse> getResponsesBySlot(@PathVariable Long slotId) {
        MeetingSlot slot = slotService.getSlotById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with Id : " + slotId));
        return responseService.getResponsesBySlot(slot);

    }

}