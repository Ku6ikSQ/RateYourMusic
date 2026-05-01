package ru.timofey.NauJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.timofey.NauJava.console.CommandProcessor;

import java.util.Scanner;

@Configuration
@Profile("!test")
public class ConsoleConfig {
    private final CommandProcessor commandProcessor;

    public ConsoleConfig(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Bean
    public CommandLineRunner commandScanner() {
        return args -> {
            Thread consoleThread = new Thread(() -> {
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.println("Введите команду. 'exit' для выхода.");
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.print("> ");
                        if (!scanner.hasNextLine()) break;
                        String input = scanner.nextLine();
                        if ("exit".equalsIgnoreCase(input.trim())) {
                            System.out.println("Выход из программы...");
                            break;
                        }
                        commandProcessor.processCommand(input);
                    }
                }
            });
            consoleThread.setDaemon(true);
            consoleThread.setName("console-scanner");
            consoleThread.start();
        };
    }
}