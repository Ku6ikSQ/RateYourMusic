package ru.timofey.NauJava.exception.report;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(Long id) {
        super("Отчет не найден: " + id);
    }
}
