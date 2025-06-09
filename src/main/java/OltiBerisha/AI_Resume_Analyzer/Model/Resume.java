package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keycloakUserId;

    private LocalDate createdAt;

    // Relationships
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Certification> certifications;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Language> languages;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL)
    private CvScore cvScore;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Feedback> feedback;
}
