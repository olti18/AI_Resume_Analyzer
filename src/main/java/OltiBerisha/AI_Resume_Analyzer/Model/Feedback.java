package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Long cvId;

    @Column(length = 3000)
    private String message;
    private LocalDateTime submittedAt;
}
