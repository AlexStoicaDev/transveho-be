//package com.example.transvehobe.rest;
//
//import com.example.transvehobe.entity.user.User;
//import com.example.transvehobe.entity.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserRepository userRepository;
//
//    @PostMapping()
//    public User createUser(@RequestBody User newUser){
//        userRepository.save(newUser);
//        return userRepository.findByEmail(newUser.getEmail());
//    }
//}
