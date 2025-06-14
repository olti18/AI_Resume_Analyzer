package OltiBerisha.AI_Resume_Analyzer.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cv{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // Keycloak ID

    private String cvFilePath;

    @Column(length = 10000)
    private String textExtracted;

    private LocalDateTime uploadDate;
}

