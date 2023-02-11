package edu.module1.service.impls;

import edu.module1.exception.ExistException;
import edu.module1.mapper.UserMapper;
import edu.module1.model.dto.UserDto;
import edu.module1.model.entity.User;
import edu.module1.model.wrapper.RegisterRequest;
import edu.module1.repository.UserRepo;
import edu.module1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    @Override
    public UserDto register(RegisterRequest request) {
        if (userRepo.existsUserByEmail(request.getEmail()))
            throw new ExistException("The user already exist with email "+ request.getEmail());

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userMapper.toUserFromRegister(request);
        userRepo.save(user);
        return userMapper.toDto(user);
    }
}
