package OltiBerisha.AI_Resume_Analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AiResumeAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiResumeAnalyzerApplication.class, args);
	}

}
