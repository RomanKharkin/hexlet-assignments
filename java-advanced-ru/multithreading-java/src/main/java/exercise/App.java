package exercise;

import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
        public static Map<String, Integer> getMinMax(int[] numbers) {
            // Создание потоков для поиска минимального и максимального элементов
            MinThread minThread = new MinThread(numbers);
            MaxThread maxThread = new MaxThread(numbers);

            // Логгирование старта потоков
            System.out.println("INFO: Thread " + minThread.getName() + " started");
            System.out.println("INFO: Thread " + maxThread.getName() + " started");

            // Запуск потоков
            minThread.start();
            maxThread.start();

            try {
                // Ожидание завершения потоков
                minThread.join();
                maxThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Логгирование окончания работы потоков
            System.out.println("INFO: Thread " + minThread.getName() + " finished");
            System.out.println("INFO: Thread " + maxThread.getName() + " finished");

            // Получение результатов из потоков
            int minNumber = minThread.getMinNumber();
            int maxNumber = maxThread.getMaxNumber();

            // Создание и возвращение словаря
            Map<String, Integer> result = new java.util.HashMap<>();
            result.put("min", minNumber);
            result.put("max", maxNumber);
            return result;
        }
    // END
}
