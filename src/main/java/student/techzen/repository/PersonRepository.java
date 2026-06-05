package student.techzen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.techzen.entity.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query(
            value = """
                    SELECT *
                    FROM people p
                    WHERE LOWER(p.full_name)
                    LIKE LOWER(CONCAT('%', :keyword, '%'))
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM people p
                    WHERE LOWER(p.full_name)
                    LIKE LOWER(CONCAT('%', :keyword, '%'))
                    """,
            nativeQuery = true
    )
    Page<Person> searchByFullName(@Param("keyword") String keyword, Pageable pageable);

}