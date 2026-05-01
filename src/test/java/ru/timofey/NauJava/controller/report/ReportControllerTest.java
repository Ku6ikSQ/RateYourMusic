package ru.timofey.NauJava.controller.report;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import ru.timofey.NauJava.entity.Report;
import ru.timofey.NauJava.entity.ReportStatus;
import ru.timofey.NauJava.repository.ReportRepository;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReportRepository reportRepository;

    private RequestSpecification spec;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();

        spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .build();
    }

    @Test
    void startReportGeneration_ShouldReturn200_WithReportId() {
        given(spec)
                .when()
                .post("/api/reports/generate")
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    void getReportContent_ShouldReturn404_WhenReportNotFound() {
        given(spec)
                .when()
                .get("/api/reports/999")
                .then()
                .log().ifValidationFails()
                .statusCode(404);
    }

    @Test
    void getReportContent_ShouldReturn202_WhenReportNotReady() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        Long id = reportRepository.save(report).getId();

        given(spec)
                .when()
                .get("/api/reports/" + id)
                .then()
                .log().ifValidationFails()
                .statusCode(202);
    }

    @Test
    void getReportContent_ShouldReturn500_WhenReportHasError() {
        Report report = new Report();
        report.setStatus(ReportStatus.ERROR);
        Long id = reportRepository.save(report).getId();

        given(spec)
                .when()
                .get("/api/reports/" + id)
                .then()
                .log().ifValidationFails()
                .statusCode(500);
    }

    @Test
    void getReportContent_ShouldReturn200_WhenReportCompleted() {
        Report report = new Report();
        report.setStatus(ReportStatus.COMPLETED);
        report.setContent("<html><body>Отчет</body></html>");
        Long id = reportRepository.save(report).getId();

        given(spec)
                .when()
                .get("/api/reports/" + id)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }
}