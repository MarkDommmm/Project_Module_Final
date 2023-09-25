package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.security.exception.CustomException;
import ra.security.model.domain.*;
import ra.security.model.dto.request.FormSignUpDto;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.model.dto.response.UserResponse;
import ra.security.repository.IOrderRepository;
import ra.security.repository.IUserRepository;
import ra.security.service.IRoleService;
import ra.security.service.IUserService;
import ra.security.service.mapper.UserMapper;

import javax.persistence.EntityExistsException;
import java.security.Principal;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;
    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Autowired
    private IRoleService roleService;

    @Override
    public Optional<Users> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users save(FormSignUpDto form) throws EntityExistsException {
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new EntityExistsException("Username is exists");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new EntityExistsException("Email is exists");
        }
        // lấy ra danh sách các quyền và chuyển thành đối tượng Users
        Set<Role> roles = new HashSet<>();
        if (form.getRoles() == null || form.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
            form.getRoles().stream().forEach(
                    role -> {
                        switch (role) {
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                            case "seller":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        }
                    }
            );
        }

        Users users = Users.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .status(true)
                .roles(roles)
                .build();
        mailService.sendMail(form.getEmail(), "HH STORE ALERT",
                "Welcome to HH STORE \n Register successfully" +
                        "\nUsername: " + form.getUsername()
                        + "\nPassword: " + form.getPassword());
        return userRepository.save(users);
    }

    public UserResponse changeStatus(Long idUser) throws CustomException {
        Optional<Users> users = userRepository.findById(idUser);
        users.get().setStatus(!users.get().isStatus());
        return userMapper.toResponse(userRepository.save(users.get()));
    }


}
