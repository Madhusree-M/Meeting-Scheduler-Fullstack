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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}