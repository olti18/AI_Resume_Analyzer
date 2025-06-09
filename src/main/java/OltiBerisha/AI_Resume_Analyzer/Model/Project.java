package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String techStack;
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}

