package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;


import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "cvs")
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String jobTitle;
    private String summary;

//    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Excperience> experiences = new ArrayList<>();>

}
