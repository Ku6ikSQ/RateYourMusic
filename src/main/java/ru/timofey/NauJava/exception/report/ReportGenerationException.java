package ru.timofey.NauJava.exception.report;

public class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(Long id) {
        super("Ошибка при формировании отчета: " + id);
    }
}
