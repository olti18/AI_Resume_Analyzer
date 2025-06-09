package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "cv_scores")
public class CvScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer experienceScore;
    private Integer educationScore;
    private Integer skillsScore;
    private Integer totalScore;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
