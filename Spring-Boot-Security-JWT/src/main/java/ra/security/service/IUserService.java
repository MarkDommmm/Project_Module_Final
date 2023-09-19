package ra.security.service;

import ra.security.model.domain.Users;
import ra.security.model.dto.request.FormSignUpDto;
import ra.security.model.dto.response.OrdersResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Optional<Users> findByUserName(String username);
    Optional<Users> findByEmail(String email);
    Users save(FormSignUpDto users);


}
