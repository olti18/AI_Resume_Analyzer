package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.JobSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSuggestionRepository extends JpaRepository<JobSuggestion,Long> {
    List<JobSuggestion> findByCvId(Long cvId);
}
