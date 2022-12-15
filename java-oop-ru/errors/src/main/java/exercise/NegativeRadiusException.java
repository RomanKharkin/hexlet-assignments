package exercise;

// BEGIN
public class NegativeRadiusException extends Exception{

private String error;
public static final NegativeRadiusException ERROR_RADIUS = new NegativeRadiusException("Invalid radius");

    public NegativeRadiusException(String error) {
        this.error = error;
    }


}
// END
