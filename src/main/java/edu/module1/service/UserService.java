package edu.module1.service;

import edu.module1.model.dto.UserDto;
import edu.module1.model.wrapper.RegisterRequest;

public interface UserService {
    UserDto register(RegisterRequest request);
}
