package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.CustomException;
import ra.security.exception.LoginException;
import ra.security.model.dto.request.FormSignInDto;
import ra.security.model.dto.request.FormSignUpDto;
import ra.security.model.dto.response.JwtResponse;
import ra.security.security.jwt.JwtProvider;
import ra.security.security.user_principle.UserPrinciple;
import ra.security.service.IUserService;
import ra.security.service.impl.MailService;
import ra.security.service.impl.UserService;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v4")
@CrossOrigin("*")

public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService iuserService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @PostMapping("/public/sign-in")
    public ResponseEntity<JwtResponse> signin(@RequestBody FormSignInDto formSignInDto, HttpSession session) throws LoginException {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            formSignInDto.getUsername(), formSignInDto.getPassword())
            ); // tạo đối tương authentiction để xác thực thông qua username va password
            // tạo token và trả về cho người dùng

            session.setAttribute("CurrentUser", formSignInDto.getUsername());
        } catch (AuthenticationException e) {
            throw new LoginException("Username or password is incorrect");
        }


        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        if (!userPrinciple.isStatus()){
            throw  new LoginException("User BLOCKKKKKKKKKK");
        }
        String token = jwtProvider.generateToken(userPrinciple);
        // lấy ra user principle
        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(JwtResponse.builder()
                .token(token)
                .name(userPrinciple.getName())
                .username(userPrinciple.getUsername())
                .roles(roles)
                .type("Bearer")
                .status(userPrinciple.isStatus()).build());
    }

    @PostMapping("/public/sign-up")
    private ResponseEntity<?> signup(@Validated @RequestBody FormSignUpDto formSignUpDto) {
        mailService.sendMail(formSignUpDto.getEmail(), "HH STORE ALERT", "Welcome to HH STORE \n Register successfully");
        iuserService.save(formSignUpDto);
        return new ResponseEntity<>("Congratulations register successfully", HttpStatus.CREATED);
    }

    @GetMapping("/admin/users/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(userService.changeStatus(id), HttpStatus.OK);
    }

}
