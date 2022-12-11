package exercise;

import java.util.Map;
import java.util.stream.Collectors;

// BEGIN
public abstract class Tag {
    protected String name;
    private final String DOUBLE_QUOTE = "\"";
    protected Map<String, String> attributes;

    public Tag(String name, Map attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String toString() {

        String attributesSerialized = attributes.entrySet()
                                              .stream()
                                              .map((attributeEntry) -> " " + attributeEntry.getKey() + "=" + DOUBLE_QUOTE
                                                                               + attributeEntry.getValue() + DOUBLE_QUOTE)
                                              .collect(Collectors.joining());

        String result = "<" + name + attributesSerialized + ">";
        return result;
    }
}
// END
