package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.security.model.domain.*;
import ra.security.model.dto.request.FormSignUpDto;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IOrderRepository;
import ra.security.repository.IUserRepository;
import ra.security.service.IRoleService;
import ra.security.service.IUserService;

import javax.persistence.EntityExistsException;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
        if (userRepository.existsByEmail(form.getEmail())){
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

        return userRepository.save(users);
    }



}
