package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "educations")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
