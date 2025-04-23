package edu.icet.controller;

import edu.icet.dto.User;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody User user) {
        boolean isSaved = userService.create(user);

        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("USER SAVED SUCCESSFULLY");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED TO SAVE THE USER");
        }
    }

    @GetMapping("/searchById/{id}")
    public ResponseEntity<User> searchById(@PathVariable Long id) {
        User user = userService.searchById(id);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody User user) {
        boolean isUpdated = userService.update(user);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("USER UPDATE SUCCESSFULLY");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED TO UPDATE THE USER");
        }
    }

    public ResponseEntity<Boolean> delete(Long id) {
        Boolean isDeleted = userService.delete(id);

        if (isDeleted != null && isDeleted) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.ok(all);
    }

}
