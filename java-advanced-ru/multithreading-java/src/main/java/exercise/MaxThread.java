package exercise;

// BEGIN
public class MaxThread extends Thread {

    private int[] numbers;
    private int maxNumber;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
        this.maxNumber = Integer.MIN_VALUE; // Инициализация максимального числа минимальным значением int
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    @Override
    public void run() {
        for (int number : numbers) {
            if (number > maxNumber) {
                maxNumber = number;
            }
        }
    }
}
// END
