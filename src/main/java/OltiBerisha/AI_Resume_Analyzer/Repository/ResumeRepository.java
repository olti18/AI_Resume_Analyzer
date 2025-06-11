package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// ResumeRepository.java
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByKeycloakUserId(String keycloakUserId);
}

