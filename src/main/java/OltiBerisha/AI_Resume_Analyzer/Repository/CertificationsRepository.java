package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationsRepository extends JpaRepository<Certification,Long> {
    List<Certification> findbyResumeId(Long resumeId);
}
