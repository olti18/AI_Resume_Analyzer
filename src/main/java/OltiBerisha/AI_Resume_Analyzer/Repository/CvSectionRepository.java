package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.CVSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CvSectionRepository extends JpaRepository<CVSection,Long> {
    List<CVSection> findByCvId(Long cvId);
}
