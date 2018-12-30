package therealfarfetchd.minicfg;

import net.minecraft.util.Identifier;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class MiniCfg {

    private Path disk;
    private Supplier<InputStream> defaultFile;

    private MiniCfgProcessor processor = new MiniCfgProcessor();

    protected final void disk(String path) {
        disk(Paths.get(path));
    }

    protected final void disk(Path path) {
        this.disk = path;
    }

    protected final void extract(Supplier<InputStream> op) {
        defaultFile = op;
    }

    protected void addInt(String prop, Consumer<Integer>... ops) {
        addCustom(prop, this::parseInt, ops);
    }

    protected void addBoolean(String prop, Consumer<Boolean>... ops) {
        addCustom(prop, this::parseBoolean, ops);
    }

    protected void addFlag(String prop, Runnable... ops) {
        add(prop, arr -> {
            for (Runnable op : ops) {
                op.run();
            }
        });
    }

    protected void addColor(String prop, Consumer<Color> op) {
        add(prop, arr -> {
            int red = arr.length < 1 ? 0 : parseInt(arr[0]);
            int green = arr.length < 2 ? 0 : parseInt(arr[1]);
            int blue = arr.length < 3 ? 0 : parseInt(arr[2]);
            int alpha = arr.length < 4 ? 255 : parseInt(arr[3]);

            op.accept(new Color(red, green, blue, alpha));
        });
    }

    protected void addIdentifier(String prop, Consumer<Identifier>... ops) {
        addCustom(prop, s -> s == null ? new Identifier("") : new Identifier(s), ops);
    }

    protected void addString(String prop, Consumer<String>... ops) {
        add(prop, arr -> {
            for (int i = 0; i < ops.length; i++) {
                Consumer<String> op = ops[i];
                if (arr.length < i) op.accept("");
                else op.accept(arr[i]);
            }
        });
    }

    protected <T> void addCustom(String prop, Function<String, T> mapper, Consumer<T>... ops) {
        add(prop, arr -> {
            for (int i = 0; i < ops.length; i++) {
                Consumer<T> op = ops[i];
                if (arr.length < i) op.accept(mapper.apply(null));
                else op.accept(mapper.apply(arr[i]));
            }
        });
    }

    protected void add(String prop, Consumer<String[]> op) {
        processor.addProperty(prop, op);
    }

    protected final void parse() {
        if (disk == null) return;
        if (!Files.exists(disk)) {
            try {
                Files.createDirectories(disk.getParent());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (defaultFile == null) return;

            try (InputStream is = defaultFile.get()) {
                Files.copy(is, disk);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            List<String> lines = Files.readAllLines(disk);
            MiniCfgParser.INSTANCE.parse(processor, lines);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private int parseInt(String s) {
        if (s == null) return 0;

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean parseBoolean(String s) {
        if (s == null) return false;

        return "true".equals(s) || "yes".equals(s) || parseInt(s) != 0;
    }

}
