package therealfarfetchd.loadingspice;

import net.minecraft.client.MinecraftClient;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.lwjgl.opengl.GL;

import therealfarfetchd.loadingspice.api.SplashUtils;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.glFlush;

public class SplashUtilsImpl implements SplashUtils {

    public static SplashUtilsImpl INSTANCE = new SplashUtilsImpl();

    private MinecraftClient mc;

    private Thread splashThread;

    private long windowHandle;
    private long tempHandle;

    private volatile boolean shouldSplashRun;
    private volatile int pause;

    private Lock renderLock = new ReentrantLock(true);

    private SplashUtilsImpl() {}

    public void start(MinecraftClient mc) {
        if (!LoadingSpiceConfig.INSTANCE.newSplash) return;

        this.mc = mc;

        shouldSplashRun = true;
        pause = 0;

        windowHandle = mc.window.getHandle();
        tempHandle = glfwCreateWindow(1, 1, "Game Load", 0, windowHandle);

        updateScreen();

        glfwHideWindow(tempHandle);
        glfwMakeContextCurrent(tempHandle);

        splashThread = new Thread(this::runSplash, "Splash Screen Renderer Thread");
        splashThread.setDaemon(true);
        splashThread.start();
    }

    public void finish() {
        if (!LoadingSpiceConfig.INSTANCE.newSplash) return;

        forceResume();

        shouldSplashRun = false;

        while (splashThread.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { }
        }

        glfwMakeContextCurrent(windowHandle);
        glfwDestroyWindow(tempHandle);
    }

    private void runSplash() {
        renderLock.lock();
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        while (shouldSplashRun) {
            updateScreen();

            if (pause != 0) {
                glFlush();
                glfwMakeContextCurrent(0);
                renderLock.unlock();
                renderLock.lock();
                glfwMakeContextCurrent(windowHandle);
            }
        }

        glfwMakeContextCurrent(0);
        renderLock.unlock();
    }

    public void pause() {
        if (!shouldSplashRun) return;

        pause++;
        if (pause != 1) return;
        renderLock.lock();
        glfwMakeContextCurrent(windowHandle);
    }

    public void resume() {
        if (!shouldSplashRun) return;

        if (pause == 0) return;
        pause--;
        if (pause != 0) return;
        glfwMakeContextCurrent(tempHandle);
        renderLock.unlock();
    }

    private void forceResume() {
        if (pause == 0) return;

        pause = 0;
        glfwMakeContextCurrent(tempHandle);
        renderLock.unlock();
    }

    private void updateScreen() {
        mc.window.method_4493(MinecraftClient.isSystemMac);
        mc.currentGui.draw(0, 0, 0.0F);
        mc.displayUpdate(false);
    }

}
