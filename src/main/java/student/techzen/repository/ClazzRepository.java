package student.techzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.techzen.entity.Clazz;

import java.util.UUID;

public interface ClazzRepository extends JpaRepository<Clazz, UUID> {
}
