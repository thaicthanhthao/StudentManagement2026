package student.techzen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.user.UserDetailResponse;
import student.techzen.dto.user.UserUpdateRequest;
import student.techzen.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}")

    public ResponseEntity<ApiResponse<UserDetailResponse>> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .data(userService.updateUser(id, request))
                .build()
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable UUID id){

        userService.deleteUser(id);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                        .success(true)
                        .data("User deactivated successfully")
                        .build()
        );
    }

}
