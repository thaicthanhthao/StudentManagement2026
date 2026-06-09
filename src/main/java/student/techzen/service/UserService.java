package student.techzen.service;

import org.springframework.stereotype.Service;
import student.techzen.dto.user.UserDetailResponse;
import student.techzen.dto.user.UserUpdateRequest;

import java.util.UUID;


@Service
public interface UserService {
    UserDetailResponse updateUser(UUID id, UserUpdateRequest request);

    void deleteUser(UUID id);
}
