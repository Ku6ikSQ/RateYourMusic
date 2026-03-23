package ru.timofey.NauJava.exception.album;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class AlbumExceptionControllerAdvice {

    @ExceptionHandler(AlbumNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAlbumNotFoundException(AlbumNotFoundException e) {
        return "Error: " + e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception e) {
        return "Internal server error: " + e.getMessage();
    }
}