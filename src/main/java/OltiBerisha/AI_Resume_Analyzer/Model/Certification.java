package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String issuer;
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}











