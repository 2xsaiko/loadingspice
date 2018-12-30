package therealfarfetchd.minicfg;

import java.util.ArrayList;
import java.util.List;

public class MiniCfgParser {

    public static final MiniCfgParser INSTANCE = new MiniCfgParser();

    private MiniCfgParser() {}

    public void parse(MiniCfgProcessor processor, List<String> lines) {
        for (String line : lines) {
            line = stripComment(line).trim();
            if (line.isEmpty()) continue;

            String[] tokens = tokenize(line);
            if (tokens.length == 0) continue;

            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, args.length);

            processor.call(tokens[0], args);
        }

    }

    private String stripComment(String in) {
        int c = in.indexOf(';');
        return c == -1 ? in : in.substring(0, c);
    }

    private String[] tokenize(String in) {
        List<String> result = new ArrayList<>();

        for (String s : in.split("\\s")) {
            if (!s.isEmpty()) result.add(s);
        }

        return result.toArray(new String[0]);
    }

}
