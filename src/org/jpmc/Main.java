package org.jpmc;

import org.jpmc.service.FileNameValidationService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        FileNameValidationService fileNameValidationService = new FileNameValidationService();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the location for file validation: ");
        String fileLocation = scanner.nextLine();
        File directory = new File(fileLocation);
        Path path = directory.toPath();
        scanner.close();
        try {
            WatchService watcher = path.getFileSystem().newWatchService();
            path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            while(true)
            {
                WatchKey k = watcher.take();
                for (WatchEvent<?> e : k.pollEvents())
                {
                    File[] files = directory.listFiles();
                    System.out.println("Validating files now...");
                    for (File file : files) {
                        if (file.isFile()) {
                            String message = fileNameValidationService.validateFileName(file.getName());
                            System.out.println(message);
                        }
                    }

                }
                k.reset();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

    }
}