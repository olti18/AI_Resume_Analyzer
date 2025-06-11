package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Education;
import OltiBerisha.AI_Resume_Analyzer.Model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education,Long> {
    List<Education> findByResumeId(Long resumeId);
}