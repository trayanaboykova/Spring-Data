package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Job;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByTitle(String jobTitle);

    @Query("SELECT j FROM Job j " +
            "WHERE j.salary >= 5000.00 " +
            "AND j.hoursAWeek <= 30.00 " +
            "ORDER BY j.salary DESC")
    List<Job> findBestJobs();
}
