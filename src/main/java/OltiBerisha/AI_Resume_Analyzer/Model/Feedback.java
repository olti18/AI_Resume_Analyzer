package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String section; // experience, education, etc.
    private String comment;
    private String suggestionLevel; // minor, major, critical

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
