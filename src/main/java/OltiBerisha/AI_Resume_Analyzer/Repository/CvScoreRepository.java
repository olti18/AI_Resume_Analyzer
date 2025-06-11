package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.CvScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CvScoreRepository extends JpaRepository<CvScore, Long> {
    Optional<CvScore> findByResumeId(Long resumeId);
}
