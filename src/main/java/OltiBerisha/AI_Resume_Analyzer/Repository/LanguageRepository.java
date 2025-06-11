package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language,Long> {
    List<Language> findByResumeId(Long resumeId);
}
