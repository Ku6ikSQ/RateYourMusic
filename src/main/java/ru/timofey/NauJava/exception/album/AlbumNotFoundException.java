package ru.timofey.NauJava.exception.album;

import ru.timofey.NauJava.exception.ResourceNotFoundException;

public class AlbumNotFoundException extends ResourceNotFoundException {
    public AlbumNotFoundException(String message) {
        super(message);
    }
}