package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project ,Long> {
    List<Project> findByResumeId(Long resumeId);
}
