package ru.timofey.NauJava.exception.report;

public class ReportNotReadyException extends RuntimeException {
    public ReportNotReadyException(Long id) {
        super("Отчет ещё формируется: " + id);
    }
}
