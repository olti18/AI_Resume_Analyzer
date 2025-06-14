package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;

    @Column(length = 5000)
    private String summary;

    @Column(length = 5000)
    private String suggestedImprovements;

    private LocalDateTime analysisDate;

    private String userId;
}

