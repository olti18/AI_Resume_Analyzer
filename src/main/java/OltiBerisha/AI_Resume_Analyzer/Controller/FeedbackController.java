//package OltiBerisha.AI_Resume_Analyzer.Controller;
//
//import OltiBerisha.AI_Resume_Analyzer.Model.Feedback;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/feedback")
//@RequiredArgsConstructor
//@Tag(name = "Feedback", description = "Let users send feedback about the AI results")
//public class FeedbackController {
//
//    private final FeedbackServ service;
//
//    @PostMapping
//    @Operation(summary = "Submit feedback")
//    public ResponseEntity<FeedbackDto> submit(@RequestBody FeedbackDto dto) {
//        return ResponseEntity.ok(service.save(dto));
//    }
//
//    @GetMapping
//    @Operation(summary = "Get your submitted feedback")
//    public ResponseEntity<List<FeedbackDto>> getMyFeedback() {
//        return ResponseEntity.ok(service.getAllForCurrentUser());
//    }
//}
//
