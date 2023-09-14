package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.security.model.domain.Users;
import ra.security.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v4/test")
@CrossOrigin("*")
public class TestController {
    @Autowired
    IUserService userService;
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Users>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }
}
