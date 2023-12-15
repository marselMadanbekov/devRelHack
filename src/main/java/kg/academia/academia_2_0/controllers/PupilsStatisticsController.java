package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.utilities.MarkDTO;
import kg.academia.academia_2_0.services.mark.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pupils/statistics")
public class PupilsStatisticsController {

    private final MarkService markService;

    @Autowired
    public PupilsStatisticsController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping("/homework-statistic")
    public ResponseEntity<List<MarkDTO>> homeworkStatisticsByPupil(@RequestParam Long pupilId,
                                                                   @RequestParam Long subjectId,
                                                                   @RequestParam Integer weekShift) {
        List<MarkDTO> markDTOS = markService.getHomeworkMarksByPupilAndSubject(pupilId, subjectId, weekShift);
        return ResponseEntity.ok(markDTOS);
    }

    @GetMapping("/classwork-statistic")
    public ResponseEntity<List<MarkDTO>> classworkStatisticsByPupil(@RequestParam Long pupilId,
                                                                    @RequestParam Long subjectId,
                                                                    @RequestParam Integer weekShift) {
        List<MarkDTO> markDTOS = markService.getClassworkMarksByPupilAndSubject(pupilId, subjectId, weekShift);
        return ResponseEntity.ok(markDTOS);
    }

    @GetMapping("/independent-work-statistic")
    public ResponseEntity<List<MarkDTO>> independentWorkStatisticsByPupil(@RequestParam Long pupilId,
                                                                          @RequestParam Long subjectId,
                                                                          @RequestParam Integer weekShift) {
        List<MarkDTO> markDTOS = markService.getIndependentWorkMarksByPupilAndSubject(pupilId, subjectId, weekShift);
        return ResponseEntity.ok(markDTOS);
    }
}
