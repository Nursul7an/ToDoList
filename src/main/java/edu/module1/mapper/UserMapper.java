package edu.module1.mapper;

import edu.module1.model.dto.UserDto;
import edu.module1.model.entity.User;
import edu.module1.model.wrapper.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUserFromRegister(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public UserDto toDto(User user) {
        return  UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
