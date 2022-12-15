package exercise;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

// BEGIN
public class Circle {
    private int x;
    private int y;
    private int radius;

    public void Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Circle(Point center, int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return this.radius;
    }

    public double getSquare() throws NegativeRadiusException {
        if (radius < 0) {
            throw NegativeRadiusException.ERROR_RADIUS;
        }
            return (PI * pow(radius, 2));
    }
}
// END
