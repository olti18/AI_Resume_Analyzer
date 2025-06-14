package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.ScrapedJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapedJobRepository extends JpaRepository<ScrapedJob,Long> {
}
