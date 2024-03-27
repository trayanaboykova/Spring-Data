package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Job;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {


    Optional<Job> findByTitle(String jobTitle);

}
