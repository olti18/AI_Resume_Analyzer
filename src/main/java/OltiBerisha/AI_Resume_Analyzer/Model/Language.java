package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String proficiency; // A1, A2, B1, B2, C1, C2

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
