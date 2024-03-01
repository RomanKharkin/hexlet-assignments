package exercise;

// BEGIN
public class ListThread implements Runnable {
    private final SafetyList list;

    public ListThread(SafetyList list) {
        this.list = list;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 1000; i++) {
            list.add((int) (Math.random() * 100));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// END
