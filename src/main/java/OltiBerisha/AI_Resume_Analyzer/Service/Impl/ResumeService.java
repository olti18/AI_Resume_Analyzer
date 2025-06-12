package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Model.Resume;
import OltiBerisha.AI_Resume_Analyzer.Repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository repository;

    public Resume save(Resume resume) {
        return repository.save(resume);
    }

    public Optional<Resume> findById(Long id) {
        return repository.findById(id);
    }

    public List<Resume> findByKeycloakUserId(String userId) {
        return repository.findByKeycloakUserId(userId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
