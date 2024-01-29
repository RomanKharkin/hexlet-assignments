package exercise;

// BEGIN
public class MinThread extends Thread {

    private int[] numbers;
    private int minNumber;

    public MinThread(int[] numbers) {
        this.numbers = numbers;
        this.minNumber = Integer.MAX_VALUE; // Инициализация минимального числа максимальным значением int
    }

    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public void run() {
        for (int number : numbers) {
            if (number < minNumber) {
                minNumber = number;
            }
        }
    }
}
// END
