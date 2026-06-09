package student.techzen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import student.techzen.dto.user.UserDetailResponse;
import student.techzen.dto.user.UserUpdateRequest;
import student.techzen.mapper.UserMapper;
import student.techzen.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final UserMapper userMapper;

    @Override
    public UserDetailResponse updateUser(UUID id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }
}
