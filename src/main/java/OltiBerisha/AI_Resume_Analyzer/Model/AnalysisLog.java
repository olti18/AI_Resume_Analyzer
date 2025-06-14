package OltiBerisha.AI_Resume_Analyzer.Model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(length = 2000)
    private String errorMessage;
}
