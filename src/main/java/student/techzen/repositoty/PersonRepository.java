package student.techzen.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import student.techzen.entity.Person;


import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
