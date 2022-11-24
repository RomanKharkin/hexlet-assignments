package exercise;

// BEGIN
public class Flat implements Home {

    private double area;
    private double balconyArea;
    private int floor;


    Flat (double area, double balconyArea, int floor) {
        this.balconyArea = balconyArea;
        this.area = area;
        this.floor = floor;
    }

    public String toString() {
        String options = "Квартира площадью " + getArea() + " метров на " + floor + " этаже";
        return options;
    }

    @Override
    public double getArea() {
        return area + balconyArea;
    }

    @Override
    public int compareTo(Home another) {
        if (getArea() > another.getArea()) {
            return 1;
        } else if (getArea() < another.getArea()) {
            return -1;
        } else {
            return 0;
        }
    }
}
// END
