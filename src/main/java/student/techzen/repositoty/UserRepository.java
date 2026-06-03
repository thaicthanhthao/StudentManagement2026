package student.techzen.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import student.techzen.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
