package OltiBerisha.AI_Resume_Analyzer.Service.Impl;


import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVRequestDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.CvMapper;
import OltiBerisha.AI_Resume_Analyzer.Mapper.ExperienceMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.CV;
import OltiBerisha.AI_Resume_Analyzer.Pdf.PdfGenerator;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CvServiceImpl  {

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private CvMapper CVMapper;

    


    public CVDto createCV(CVRequestDto cvRequestDto) {
        CV cv = CVMapper.toEntity(cvRequestDto);
        cv.setUserId(KeycloakUtils.getCurrentUserId());
        CV saved = cvRepository.save(cv);
        return CVMapper.toDto(saved);
    }

//    public List<CVDto> getAllCVs() {
//        return cvRepository.findAll()
//                .stream()
//                .map(CVMapper::toDto)
//                .collect(Collectors.toList());
//    }


    public CVDto getCVById(Long id) {
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));
        return CVMapper.toDto(cv);
    }

    public CVDto updateCV(Long id, CVRequestDto cvRequestDto) {
        CV existing = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));

        // Update fields
        existing.setFullname(cvRequestDto.getFullname());
        existing.setEmail(cvRequestDto.getEmail());
        existing.setPhoneNumber(cvRequestDto.getPhoneNumber());
        existing.setJobTitle(cvRequestDto.getJobTitle());
        existing.setSummary(cvRequestDto.getSummary());

        // Update experiences
        existing.getExperiences().clear();
        cvRequestDto.getExperiences().forEach(expDto -> {
            var experience = ExperienceMapper.toEntity(expDto);
            experience.setCv(existing);
            existing.getExperiences().add(experience);
        });

        CV saved = cvRepository.save(existing);
        return CVMapper.toDto(saved);
    }

    public void deleteCV(Long id) {
        cvRepository.deleteById(id);
    }


}
