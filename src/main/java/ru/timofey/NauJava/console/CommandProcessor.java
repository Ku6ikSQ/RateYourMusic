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
            System.out.println("Введите команду. Используйте 'help'.");
            return;
        }

        try {
            switch (cmd[0].toLowerCase()) {
                case "create" -> handleCreate(cmd);
                case "find" -> handleFind(cmd);
                case "delete" -> handleDelete(cmd);
                case "rename" -> handleRename(cmd);
                case "list" -> handleList();
                case "help" -> handleHelp();
                default -> System.out.println("Неизвестная команда. Используйте 'help'.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат числа (id или duration должны быть числами).");

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: недостаточно аргументов. Используйте 'help' для справки.");

        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }

    private void handleCreate(String[] cmd) {
        validateArgs(cmd, 4,
                "Команда 'create' принимает 3 аргумента: <title> <author> <duration>");

        String title = cmd[1];
        String author = cmd[2];
        int duration = Integer.parseInt(cmd[3]);

        trackService.createTrack(title, author, duration);
        System.out.println("Трек успешно добавлен!");
    }

    private void handleFind(String[] cmd) {
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

    private void handleDelete(String[] cmd) {
        validateArgs(cmd, 2,
                "Команда 'delete' принимает 1 аргумент: <id>");

        Long id = Long.valueOf(cmd[1]);
        trackService.deleteById(id);
        System.out.println("Трек удалён.");
    }

    private void handleRename(String[] cmd) {
        validateArgs(cmd, 3,
                "Команда 'rename' принимает 2 аргумента: <id> <newTitle>");

        Long id = Long.valueOf(cmd[1]);
        String newTitle = cmd[2];

        trackService.renameTrack(id, newTitle);
        System.out.println("Название трека обновлено.");
    }

    private void handleList() {
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

    private void handleHelp() {
        System.out.println("""
                Доступные команды:
                
                create <title> <author> <duration:int>
                    Создаёт новый трек.
                    title     - название трека (String)
                    author    - автор трека (String)
                    duration  - длительность в секундах (int)
                    Возвращает: ничего (только сообщение об успешном создании)
                
                find <id:long>
                    Находит трек по идентификатору.
                    id - идентификатор трека (Long)
                    Возвращает: информация о треке или сообщение "не найден"
                
                delete <id:long>
                    Удаляет трек по идентификатору.
                    id - идентификатор трека (Long)
                    Возвращает: ничего (сообщение об удалении)
                
                rename <id:long> <newTitle>
                    Обновляет название трека.
                    id        - идентификатор трека (Long)
                    newTitle  - новое название (String)
                    Возвращает: ничего (сообщение об обновлении)
                
                list
                    Выводит список всех треков.
                    Аргументы: отсутствуют
                    Возвращает: список треков
                
                help
                    Показывает список доступных команд.
                    Аргументы: отсутствуют
                    Возвращает: справочную информацию
                """);
    }

    private void validateArgs(String[] cmd, int expectedLength, String message) {
        if (cmd.length < expectedLength) {
            throw new IllegalArgumentException(message);
        }
    }
}