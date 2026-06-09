package student.techzen.mapper;

import org.springframework.stereotype.Component;
import student.techzen.dto.user.UserDetailResponse;
import student.techzen.entity.User;

@Component
public class UserMapper {

    public UserDetailResponse toDetailResponse(User user) {

        return UserDetailResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
