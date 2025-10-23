package com.example.Meeting_Scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Meeting_Scheduler.entity.SlotResponse;
import com.example.Meeting_Scheduler.service.MeetingSlotService;
import com.example.Meeting_Scheduler.service.SlotResponseService;

@Controller
@RequestMapping("/responses")
public class SlotResponseController {

    @Autowired
    SlotResponseService responseService;

    @Autowired
    MeetingSlotService slotService;

    // Display all Responses
    @GetMapping("/list")
    public String getAllResponses(Model model) {
        model.addAttribute("responses", responseService.getAllResponses());
        return "/responses/list";
    }

    // Form for new response
    @GetMapping("/form")
    public String showResponseForm(Model model) {
        model.addAttribute("responses", new SlotResponse());
        model.addAttribute("slots", slotService.getAllMeetingSlots());
        return "/responses/form";
    }

}
