package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Model.Experience;
import OltiBerisha.AI_Resume_Analyzer.Repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {
    @Autowired
    private ExperienceRepository repository;

    public Experience save(Experience exp) {
        return repository.save(exp);
    }

    public List<Experience> findByResumeId(Long resumeId) {
        return repository.findByResumeId(resumeId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

