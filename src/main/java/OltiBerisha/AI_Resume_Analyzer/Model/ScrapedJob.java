package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapedJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String jobTitle;
    private String company;
    private String location;

    @Column(length = 5000)
    private String description;

    private String jobUrl;
    private LocalDateTime scrapeDate;
}

