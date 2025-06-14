package OltiBerisha.AI_Resume_Analyzer.Repository;

import OltiBerisha.AI_Resume_Analyzer.Model.FavoriteJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteJobRepository extends JpaRepository<FavoriteJob,Long> {
    List<FavoriteJob> findByUserId(String userId);
}
