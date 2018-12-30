package therealfarfetchd.loadingspice.api;

public interface SplashUtils {

    SplashUtils INSTANCE = APIInternals.getSplashUtils();

    /**
     * Call this if you need to do initialization on the main thread that requires the game window's GL context (e.g. VBO init)
     */
    void pause();

    /**
     * Call this after you're done initializing to let the loading screen continue updating.
     */
    void resume();

}
