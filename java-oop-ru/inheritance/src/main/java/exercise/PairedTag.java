package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public final class PairedTag extends Tag {

    private String body;
    private List<Tag> children;

    public PairedTag(String name, Map data, String body, List<Tag> children) {
        super(name, data);
        this.body = body;
        this.children = children;
    }


    @Override
    public String toString() {

        String openTagSerialized = super.toString();
        String childrenSerialized = children.stream().map(String::valueOf).collect(Collectors.joining());
        String closeTagSerialized = "</" + name + ">";

        return  openTagSerialized + childrenSerialized + body + closeTagSerialized;
    }
}
// END
