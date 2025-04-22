package edu.icet.controller;

import edu.icet.dto.User;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {
    private final UserService userService;

    public ResponseEntity<String> createUser(User user) {
        boolean isSaved = userService.createUser(user);

        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("USER SAVED SUCCESSFULLY");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED TO SAVE THE USER");
        }
    }

    @GetMapping("get-all")
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.ok(all);
    }

}
