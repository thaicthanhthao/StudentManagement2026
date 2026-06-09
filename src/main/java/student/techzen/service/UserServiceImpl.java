package student.techzen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.user.UserDetailResponse;
import student.techzen.dto.user.UserUpdateRequest;
import student.techzen.entity.User;
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

        User user = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (request.getUsername() != null) {

            if (request.getUsername().equals(user.getUsername()) ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
            }

            user.setUsername(request.getUsername());
        }

        if(request.getEmail() != null){

            if(!request.getEmail().equals(user.getEmail()) && userRepo.existsByEmail(request.getEmail())){

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
            }

            user.setEmail(request.getEmail());
        }

        if(request.getRole() != null){

            user.setRole(request.getRole());
        }

        user = userRepo.save(user);

        return userMapper.toDetailResponse(user);
    }

    @Override
    public void deleteUser(UUID id) {

        User user = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if("INACTIVE".equals(user.getStatus())){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already inactive");

        }

        user.setStatus("INACTIVE");

        userRepo.save(user);
    }
}
