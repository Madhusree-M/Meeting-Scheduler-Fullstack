// package com.example.Meeting_Scheduler.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.Meeting_Scheduler.entity.User;
// import com.example.Meeting_Scheduler.service.UserService;

// @Controller
// @RequestMapping("/api/users")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     // Display all users
//     @GetMapping("/list")
//     public String getAllUsers(Model model) {
//         model.addAttribute("users", userService.getAllUsers());
//         return "users/list";
//     }

//     // Add User Form
//      @GetMapping("/form")
//     public String createUserForm(Model model) {
//         model.addAttribute("user", new User());
//         return "users/form";
//     }

//     // Save user to database
//     @PostMapping
//     public String createUser(@ModelAttribute("user") User user) {
//         userService.createUser(user);
//         return "redirect:/users";
//     }

//     // Edit User
//     @GetMapping("/edit/{id}")
//     public String editUserForm(@PathVariable Long id, Model model) {
//         User user = userService.getUserById(id)
//                 .orElseThrow(() -> new RuntimeException("User not found with ID : " + id));
//         model.addAttribute("user", user);
//         return "users/form";
//     }

//     // Update existing user
//     @PostMapping("/update/{id}")
//     public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
//         user.setUserId(id);
//         userService.createUser(user);
//         return "redirect:/users";
//     }

//     // Delete user
//     @GetMapping("/delete/{id}")
//     public String deleteUser(@PathVariable Long id) {
//         userService.deleteUser(id);
//         return "redirect:/users";
//     }

// }

// The Below code is for postman testing

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

import com.example.Meeting_Scheduler.entity.User;
import com.example.Meeting_Scheduler.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Display all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Create new User
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Get user by Id
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID : " + id));
    }

    // Update existing user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        return userService.createUser(user);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully.!!";
    }

}
