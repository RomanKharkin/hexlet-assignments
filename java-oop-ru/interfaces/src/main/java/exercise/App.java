package exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class App {

    public static List<String> buildAppartmentsList(List<Home> apartments, int amount) {
        System.out.println(apartments);
        List<String> collect = apartments.stream()
                .sorted((another1, another2) -> another1.compareTo(another2))
//                .sorted(Comparator.comparing(a -> a.getArea()))
                .limit(amount)
                .map(x -> x.toString())
                .collect(Collectors.toList());

        return collect;
    }
}
// END
