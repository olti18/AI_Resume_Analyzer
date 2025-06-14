package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.CVAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CVAnalysisResultRepository extends JpaRepository<CVAnalysisResult,Long> {
    List<CVAnalysisResult> findByCvId(Long cvId);
    List<CVAnalysisResult> findByCvIdAndUserId(Long cvId, String userId);
}
