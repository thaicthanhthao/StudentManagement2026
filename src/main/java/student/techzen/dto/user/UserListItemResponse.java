package student.techzen.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListItemResponse {

    private UUID id;

    private String username;

    private String email;

    private String role;

    private String status;
}
