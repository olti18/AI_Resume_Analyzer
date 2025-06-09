package OltiBerisha.AI_Resume_Analyzer.Model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "experiences")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}


















//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity(name = "experience")
//public class Experience {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String position;
//    private String company;
//    private String years;
//
//    @ManyToOne
//    @JoinColumn(name = "cv_id")
//    private CV cv;
//
//}
