package ru.timofey.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.timofey.NauJava.service.TrackService;
import ru.timofey.NauJava.entities.Track;

import java.util.List;

@Component
public class CommandProcessor {

    private final TrackService trackService;

    @Autowired
    public CommandProcessor(TrackService trackService) {
        this.trackService = trackService;
    }

    public void processCommand(String input) {
        String[] cmd = input.trim().split(" ");

        if (cmd.length == 0 || cmd[0].isBlank()) {
            System.out.println("Введите команду.");
            return;
        }

        try {
            switch (cmd[0].toLowerCase()) {

                case "create" -> {
                    // create <title> <author> <duration>
                    validateArgs(cmd, 4,
                            "Команда 'create' принимает 3 аргумента: <title> <author> <duration>");

                    String title = cmd[1];
                    String author = cmd[2];
                    int duration = Integer.parseInt(cmd[3]);

                    trackService.createTrack(title, author, duration);
                    System.out.println("Трек успешно добавлен!");
                }

                case "find" -> {
                    // find <id>
                    validateArgs(cmd, 2,
                            "Команда 'find' принимает 1 аргумент: <id>");

                    Long id = Long.valueOf(cmd[1]);
                    Track track = trackService.findById(id);

                    if (track != null) {
                        System.out.printf(
                                "Найден трек: id=%d, title=%s, author=%s, duration=%d%n",
                                track.getId(),
                                track.getTitle(),
                                track.getAuthor(),
                                track.getDuration()
                        );
                    } else {
                        System.out.println("Трек не найден.");
                    }
                }

                case "delete" -> {
                    // delete <id>
                    validateArgs(cmd, 2,
                            "Команда 'delete' принимает 1 аргумент: <id>");

                    Long id = Long.valueOf(cmd[1]);
                    trackService.deleteById(id);
                    System.out.println("Трек удалён.");
                }

                case "rename" -> {
                    // rename <id> <newTitle>
                    validateArgs(cmd, 3,
                            "Команда 'rename' принимает 2 аргумента: <id> <newTitle>");

                    Long id = Long.valueOf(cmd[1]);
                    String newTitle = cmd[2];

                    trackService.renameTrack(id, newTitle);
                    System.out.println("Название трека обновлено.");
                }

                case "list" -> {
                    // list
                    List<Track> tracks = trackService.findAll();

                    if (tracks.isEmpty()) {
                        System.out.println("Список треков пуст.");
                    } else {
                        tracks.forEach(track ->
                                System.out.printf(
                                        "id=%d, title=%s, author=%s, duration=%d%n",
                                        track.getId(),
                                        track.getTitle(),
                                        track.getAuthor(),
                                        track.getDuration()
                                )
                        );
                    }
                }

                default -> System.out.println("Введена неизвестная команда.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }

    private void validateArgs(String[] cmd, int expectedLength, String message) {
        if (cmd.length < expectedLength) {
            throw new IllegalArgumentException(message);
        }
    }
}