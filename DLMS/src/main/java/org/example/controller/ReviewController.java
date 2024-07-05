package org.example.controller;

import org.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.HorizontalProject;
import org.example.entity.Paper;
import org.example.entity.Patent;
import org.example.entity.VerticalProject;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final PaperService paperService;
    private final PatentService patentService;
    // 注入其它实体的服务
    private final HorizontalProjectService horizontalProjectService;
    private final VerticalProjectService verticalProjectService;
    public ReviewController(PaperService paperService,
                            PatentService patentService,
                            HorizontalProjectService horizontalProjectService,
                            VerticalProjectService verticalProjectService,ReviewService reviewService) {
        this.paperService = paperService;
        this.patentService = patentService;
        this.horizontalProjectService = horizontalProjectService;
        this.verticalProjectService = verticalProjectService;
        this.reviewService=reviewService;
    }

    @GetMapping("/papers")
    public ResponseEntity<List<Paper>> getPendingPapers() {
        List<Paper> pendingPapers = reviewService.getPendingPapers();
        return new ResponseEntity<>(pendingPapers, HttpStatus.OK);
    }

    @GetMapping("/patents")
    public ResponseEntity<List<Patent>> getPendingPatents() {
        List<Patent> pendingPatents = reviewService.getPendingPatents();
        return new ResponseEntity<>(pendingPatents, HttpStatus.OK);
    }

    @GetMapping("/horizontal-projects")
    public ResponseEntity<List<HorizontalProject>> getPendingHorizontalProjects() {
        List<HorizontalProject> pendingHorizontalProjects = reviewService.getPendingHorizontalProjects();
        return new ResponseEntity<>(pendingHorizontalProjects, HttpStatus.OK);
    }

    @GetMapping("/vertical-projects")
    public ResponseEntity<List<VerticalProject>> getPendingVerticalProjects() {
        List<VerticalProject> pendingVerticalProjects = reviewService.getPendingVerticalProjects();
        return new ResponseEntity<>(pendingVerticalProjects, HttpStatus.OK);
    }
    @PostMapping("/papers/{id}")
    public void updatePaperReviewStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("Review Status");
        paperService.reviewPaper(id, status);
    }

    @PostMapping("/patents/{id}")
    public void updatePatentReviewStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("Review Status");
        patentService.reviewPatent(id, status);
    }

    @PostMapping("/horizontal-projects/{id}")
    public void updateHorizontalProjectReviewStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("Review Status");
        horizontalProjectService.reviewProject(id, status);
        System.out.println("Status obtained in the method is: " + status);
    }

    @PostMapping("/vertical-projects/{id}")
    public void updateVerticalProjectReviewStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("Review Status");
        verticalProjectService.reviewProject(id, status);
    }
}