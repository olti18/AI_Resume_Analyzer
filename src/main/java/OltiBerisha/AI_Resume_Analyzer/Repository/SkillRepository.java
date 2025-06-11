package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// SkillRepository.java
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByResumeId(Long resumeId);
}
