package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.ScrapeSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapedSourceRepository extends JpaRepository<ScrapeSource,Long> {
}
