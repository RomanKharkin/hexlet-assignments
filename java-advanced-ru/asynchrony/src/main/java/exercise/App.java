package exercise;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

class App {
    // BEGIN
    // Асинхронный метод для объединения содержимого двух файлов в третий файл
    public static CompletableFuture<String> unionFiles(String file1, String file2, String dest) {
        // Создаем объекты Path из строковых аргументов
        Path path1 = Paths.get(file1);
        Path path2 = Paths.get(file2);
        Path path3 = Paths.get(dest);

        // Создаем CompletableFuture, который асинхронно читает содержимое первого файла
        CompletableFuture<String> read1 = CompletableFuture.supplyAsync(() -> {
            try {
                return Files.readString(path1);
            } catch (IOException e) {
                // Выводим сообщение об ошибке, если файл не существует или не может быть прочитан
                System.out.println("Ошибка при чтении файла " + file1 + ": " + e.getClass().getName() + ": " + e.getMessage());
                return "";
            }
        });
        // Создаем CompletableFuture, который асинхронно читает содержимое второго файла
        CompletableFuture<String> read2 = CompletableFuture.supplyAsync(() -> {
            try {
                return Files.readString(path2);
            } catch (IOException e) {
                // Выводим сообщение об ошибке, если файл не существует или не может быть прочитан
                System.out.println("Ошибка при чтении файла " + file2 + ": " + e.getMessage());
                return "";
            }
        });
        // Создаем CompletableFuture, который асинхронно объединяет содержимое двух файлов и записывает его в третий файл
        CompletableFuture<String> write = read1.thenCombine(read2, (s1, s2) -> {
            // Объединяем строки из двух файлов
            String s3 = s1 + s2;
            try {
                // Проверяем, существует ли третий файл, и если нет, то создаем его
                if (!Files.exists(path3)) {
                    Files.createFile(path3);
                }
                // Записываем объединенную строку в третий файл
                Files.writeString(path3, s3);
                // Возвращаем сообщение об успешной записи
                return "Файл " + dest + " успешно записан";
            } catch (IOException e) {
                // Выводим сообщение об ошибке, если файл не может быть создан или записан
                System.out.println("Ошибка при записи файла " + dest + ": " + e.getMessage());
                return "";
            }
        });
        // Возвращаем CompletableFuture, который будет выполнен после завершения всех предыдущих
        return write;
    }
    // END

    public static void main(String[] args) {
        // BEGIN
            String destinationFilePath = "src/main/resources/file3.txt";
            // Вызываем асинхронный метод для объединения содержимого двух файлов в третий файл
            CompletableFuture<String> result = unionFiles("src/main/resources/file1.txt", "src/main/resources/file2.txt", "src/main/resources/file3.txt");
            // Получаем результат асинхронного метода и выводим его на экран
            result.thenAccept(System.out::println);
        // END
    }
}

