package ru.timofey.NauJava.service.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.timofey.NauJava.exception.report.ReportNotFoundException;
import ru.timofey.NauJava.exception.report.ReportNotReadyException;
import ru.timofey.NauJava.repository.ReportRepository;

@SpringBootTest
@ActiveProfiles("test")
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
    }

    @Test
    void testCreate_SavesReportWithCreatedStatus() {
        Long id = reportService.create();

        Assertions.assertNotNull(id);
        Assertions.assertEquals(1, reportRepository.count());
    }

    @Test
    void testGetContent_NotFound_ThrowsReportNotFoundException() {
        Assertions.assertThrows(ReportNotFoundException.class, () -> reportService.getContent(999L));
    }

    @Test
    void testGetContent_NotReady_ThrowsReportNotReadyException() {
        Long id = reportService.create();

        Assertions.assertThrows(ReportNotReadyException.class, () -> reportService.getContent(id));
    }
}
