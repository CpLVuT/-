package org.example.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.HorizontalProject;
import org.example.entity.Paper;
import org.example.entity.Patent;
import org.example.entity.VerticalProject;
import org.example.service.HorizontalProjectService;
import org.example.service.PaperService;
import org.example.service.PatentService;
import org.example.service.VerticalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ReviewService {
    private final PaperService paperService;
    private final PatentService patentService;
    private final HorizontalProjectService horizontalProjectService;
    private final VerticalProjectService verticalProjectService;

    @Autowired
    public ReviewService(PaperService paperService, PatentService patentService,
                         HorizontalProjectService horizontalProjectService,
                         VerticalProjectService verticalProjectService) {
        this.paperService = paperService;
        this.patentService = patentService;
        this.horizontalProjectService = horizontalProjectService;
        this.verticalProjectService = verticalProjectService;
    }

    public List<Paper> getPendingPapers() {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_status", "PENDING");
        return paperService.list(queryWrapper);
    }

    public List<Patent> getPendingPatents() {
        QueryWrapper<Patent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_status", "PENDING");
        return patentService.list(queryWrapper);
    }

    public List<HorizontalProject> getPendingHorizontalProjects() {
        QueryWrapper<HorizontalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_status", "PENDING");
        return horizontalProjectService.list(queryWrapper);
    }

    public List<VerticalProject> getPendingVerticalProjects() {
        QueryWrapper<VerticalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_status", "PENDING");
        return verticalProjectService.list(queryWrapper);
    }
    @PostMapping("/papers/{id}")
    public void updatePaperReviewStatus(@PathVariable Long id, @RequestParam("review status") String status) {
        paperService.reviewPaper(id, status);
    }

    @PostMapping("/patents/{id}")
    public void updatePatentReviewStatus(@PathVariable Long id, @RequestParam("review status") String status) {
        patentService.reviewPatent(id, status);
    }

    //其它实体的状态修改
    @PostMapping("/horizontal-projects/{id}")
    public void updateHorizontalProjectReviewStatus(@PathVariable Long id, @RequestParam("review status") String status) {
        horizontalProjectService.reviewProject(id, status);
    }

    @PostMapping("/vertical-projects/{id}")
    public void updateVerticalProjectReviewStatus(@PathVariable Long id, @RequestParam("review status") String status) {
        verticalProjectService.reviewProject(id, status);
    }
}

