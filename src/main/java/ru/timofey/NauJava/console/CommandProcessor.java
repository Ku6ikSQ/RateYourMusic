package ru.timofey.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.timofey.NauJava.entity.Track;
import ru.timofey.NauJava.service.track.TrackService;

@Component
public class CommandProcessor {

    private final TrackService trackService;

    @Autowired
    public CommandProcessor(TrackService trackService) {
        this.trackService = trackService;
    }

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        try {
            switch (cmd[0].toLowerCase()) {

                case "create" -> {
                    // create <title> <duration>
                    String title = cmd[1];
                    int duration = Integer.parseInt(cmd[2]);
                    trackService.createTrack(title, duration);
                    System.out.println("Трек успешно добавлен!");
                }

                case "find" -> {
                    // find <id>
                    Long id = Long.valueOf(cmd[1]);
                    Track track = trackService.findById(id);
                    if (track != null) {
                        String author = trackService.getTrackAuthor(id);
                        System.out.printf("Найден трек: id=%d, title=%s, author=%s, duration=%d%n",
                                track.getId(), track.getTitle(), author, track.getDurationSeconds());
                    } else {
                        System.out.println("Трек не найден.");
                    }
                }

                case "delete" -> {
                    // delete <id>
                    Long id = Long.valueOf(cmd[1]);
                    trackService.deleteById(id);
                    System.out.println("Трек удалён.");
                }

                case "rename" -> {
                    // rename <id> <newTitle>
                    Long id = Long.valueOf(cmd[1]);
                    String newTitle = cmd[2];
                    trackService.renameTrack(id, newTitle);
                    System.out.println("Название трека обновлено.");
                }

                default -> System.out.println("Введена неизвестная команда.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при обработке команды: " + e.getMessage());
        }
    }
}