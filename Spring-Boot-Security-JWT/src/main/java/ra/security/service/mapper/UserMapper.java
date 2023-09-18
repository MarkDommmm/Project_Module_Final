package ra.security.service.mapper;

import ra.security.model.domain.Users;
import ra.security.model.dto.request.UserRequest;
import ra.security.model.dto.response.UserResponse;
import ra.security.service.IGenericMapper;

public class UserMapper implements IGenericMapper<Users, UserRequest, UserResponse> {
    @Override
    public Users toEntity(UserRequest userRequest) {
        return Users.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .roles(userRequest.getRoles())
                .password(userRequest.getPassword())
                .shipments(userRequest.getShipments())
                .orders(userRequest.getOrders())
                .status(userRequest.isStatus())
                .build();
    }

    @Override
    public UserResponse toResponse(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                .name(users.getName())
                .shipments(users.getShipments())
                .roles(users.getRoles())
                .status(users.isStatus())
                .build();
    }
}
