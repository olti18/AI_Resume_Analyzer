package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// ExperienceRepository.java
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByResumeId(Long resumeId);
}
