package ru.timofey.NauJava.exception.report;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ReportExceptionControllerAdvice {

    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleReportNotFoundException(ReportNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ReportNotReadyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String handleReportNotReadyException(ReportNotReadyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ReportGenerationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleReportGenerationException(ReportGenerationException e) {
        return e.getMessage();
    }
}
