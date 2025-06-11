package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByResumeId(Long resumeId);
}
