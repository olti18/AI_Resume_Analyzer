package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.AnalysisLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisLogRepository extends JpaRepository<AnalysisLog,Long> {
    List<AnalysisLog> findByCvId(Long cvId);
}
