package exercise;

// BEGIN
public class Cottage implements Home {

    private double area;

    private int floorCount;


    public Cottage (double area, int floorCount) {
        this.area = area;
        this.floorCount = floorCount;
    }


    public String toString() {
        String options = floorCount + " этажный коттедж площадью " + area + " метров";
        return options;
    }

    @Override
    public double getArea() {
        return area;
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
