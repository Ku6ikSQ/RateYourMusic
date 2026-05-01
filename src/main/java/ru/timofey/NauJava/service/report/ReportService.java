package ru.timofey.NauJava.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.timofey.NauJava.entity.Report;
import ru.timofey.NauJava.entity.ReportStatus;
import ru.timofey.NauJava.entity.Track;
import ru.timofey.NauJava.repository.ReportRepository;
import ru.timofey.NauJava.repository.UserRepository;
import ru.timofey.NauJava.service.track.TrackService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TrackService trackService;
    private final TemplateEngine templateEngine;


    @Autowired
    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         TrackService trackService,
                         TemplateEngine templateEngine) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.trackService = trackService;
        this.templateEngine = templateEngine;
    }

    public Long create() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report = reportRepository.save(report);
        return report.getId();
    }

    public String getContent(Long id) {
        return reportRepository.findById(id)
                .map(report -> {
                    if (report.getStatus() == ReportStatus.CREATED) return "Отчет еще формируется...";
                    if (report.getStatus() == ReportStatus.ERROR) return "Ошибка при формировании отчета";
                    return report.getContent();
                })
                .orElse("Отчет не найден");
    }

    public void generateReportAsync(Long reportId) {
        CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            Report report = reportRepository.findById(reportId).orElseThrow();

            try {
                AtomicLong usersCount = new AtomicLong();
                long userTaskStart = System.currentTimeMillis();
                Thread userThread = new Thread(() -> usersCount.set(userRepository.count()));
                userThread.start();

                AtomicReference<List<Track>> tracksList = new AtomicReference<>();
                long trackTaskStart = System.currentTimeMillis();
                Thread trackThread = new Thread(() -> tracksList.set(trackService.findAll()));
                trackThread.start();

                userThread.join();
                long userTaskElapsed = System.currentTimeMillis() - userTaskStart;

                trackThread.join();
                long trackTaskElapsed = System.currentTimeMillis() - trackTaskStart;

                Context context = new Context();
                context.setVariable("userCount", usersCount.get());
                context.setVariable("userTime", userTaskElapsed);
                context.setVariable("tracks", tracksList.get());
                context.setVariable("trackCount", tracksList.get().size());
                context.setVariable("trackTime", trackTaskElapsed);
                context.setVariable("totalTime", System.currentTimeMillis() - startTime);

                String htmlContent = templateEngine.process("report/report", context);

                report.setContent(htmlContent);
                report.setStatus(ReportStatus.COMPLETED);

            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
            }
            reportRepository.save(report);
        });
    }
}