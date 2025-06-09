package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String level; // beginner, intermediate, expert

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
