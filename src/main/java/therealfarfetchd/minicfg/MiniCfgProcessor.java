package therealfarfetchd.minicfg;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MiniCfgProcessor {

    private Map<String, Consumer<String[]>> props = new HashMap<>();

    public void addProperty(String property, Consumer<String[]> op) {
        props.put(property, op);
    }

    public void call(String property, String... args) {
        Consumer<String[]> c = props.get(property);
        if (c == null) return;
        c.accept(args);
    }

}
