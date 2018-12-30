package therealfarfetchd.loadingspice;

import net.minecraft.util.Identifier;

import java.awt.Color;

import therealfarfetchd.minicfg.MiniCfg;

public class LoadingSpiceConfig extends MiniCfg {

    public static final LoadingSpiceConfig INSTANCE = new LoadingSpiceConfig();

    public boolean newSplash = false;

    public boolean verboseLoad = false;

    public boolean useExtTextureDir = false;
    public String extTextureDir = "ext_resources";

    public Identifier loadingIconTexture = new Identifier(LoadingSpice.MODID, "textures/loadingcircle.png");
    public Identifier splashIconTexture = new Identifier(LoadingSpice.MODID, "textures/loadingcircle.png");

    public int[] loadingIconGrid = {4, 4};
    public int[] splashIconGrid = {4, 4};

    public int[] loadingIconSize = {32, 32};
    public int[] splashIconSize = {32, 32};

    public int[] loadingIconAlign = {-16, -16};
    public int[] splashIconAlign = {-16, -16};

    public int loadingIconAnimSpeed = 16;
    public int splashIconAnimSpeed = 16;

    public Color loadingIconColor = Color.WHITE;
    public Color splashIconColor = new Color(50, 50, 50, 255);

    public Color loadingTextColor = Color.WHITE;
    public Color splashTextColor = Color.BLACK;

    @SuppressWarnings("unchecked")
    private LoadingSpiceConfig() {
        disk("config/loading_screen.cfg");
        extract(() -> LoadingSpiceConfig.class.getResourceAsStream("/default_config"));

        addFlag("new_splash", () -> newSplash = true);

        addFlag("sp_load_more_info", () -> verboseLoad = true);

        addFlag("use_ext_texture_dir", () -> useExtTextureDir = true);
        addString("ext_texture_dir", $ -> extTextureDir = $);

        addIdentifier("gen_load_texture", $ -> loadingIconTexture = $);
        addIdentifier("splash_load_texture", $ -> splashIconTexture = $);

        addInt("gen_load_texture_layout", $ -> loadingIconGrid[0] = $, $ -> loadingIconGrid[1] = $);
        addInt("splash_load_texture_layout", $ -> splashIconGrid[0] = $, $ -> splashIconGrid[1] = $);

        addInt("gen_load_texture_size", $ -> loadingIconSize[0] = $, $ -> loadingIconSize[1] = $);
        addInt("splash_load_texture_size", $ -> splashIconSize[0] = $, $ -> splashIconSize[1] = $);

        addInt("gen_load_texture_align", $ -> loadingIconAlign[0] = $, $ -> loadingIconAlign[1] = $);
        addInt("splash_load_texture_align", $ -> splashIconAlign[0] = $, $ -> splashIconAlign[1] = $);

        addInt("gen_load_texture_speed", $ -> loadingIconAnimSpeed = $);
        addInt("splash_load_texture_speed", $ -> splashIconAnimSpeed = $);

        addColor("gen_load_texture_color", $ -> loadingIconColor = $);
        addColor("splash_load_texture_color", $ -> splashIconColor = $);

        addColor("load_text_color", $ -> loadingTextColor = $);
        addColor("splash_text_color", $ -> splashTextColor = $);

        parse();
    }

}
