package ru.timofey.NauJava.controller.report;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timofey.NauJava.service.report.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Long> startReportGeneration() {
        Long reportId = reportService.create();
        reportService.generateReportAsync(reportId);

        return ResponseEntity.ok(reportId);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
    public ResponseEntity<String> getReportContent(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getContent(id));
    }
}