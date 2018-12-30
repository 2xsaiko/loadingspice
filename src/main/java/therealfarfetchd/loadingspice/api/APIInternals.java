package therealfarfetchd.loadingspice.api;

import net.fabricmc.loader.FabricLoader;

import therealfarfetchd.loadingspice.LoadingProgressImpl;
import therealfarfetchd.loadingspice.LoadingSpice;
import therealfarfetchd.loadingspice.SplashUtilsImpl;

public class APIInternals {

    static LoadingProgress getLoadingProgress() {
        if (FabricLoader.INSTANCE.isModLoaded(LoadingSpice.MODID)) {
            return LoadingProgressImpl.INSTANCE;
        } else {
            return DummyLoadingProgress.INSTANCE;
        }
    }

    static SplashUtils getSplashUtils() {
        if (FabricLoader.INSTANCE.isModLoaded(LoadingSpice.MODID)) {
            return SplashUtilsImpl.INSTANCE;
        } else {
            return DummySplashUtils.INSTANCE;
        }
    }

    private static class DummyLoadingProgress implements LoadingProgress {

        @Override
        public TaskInfo pushTask() {
            return DummyTaskInfo.INSTANCE;
        }

        @Override
        public void popTask(TaskInfo ti) {}

        @Override
        public void popTask() {}

        private static class DummyTaskInfo implements TaskInfo {

            public static final DummyTaskInfo INSTANCE = new DummyTaskInfo();

            @Override
            public TaskInfo withTaskName(String name) {
                return this;
            }

        }

    }

    private static class DummySplashUtils implements SplashUtils {

        public static final DummySplashUtils INSTANCE = new DummySplashUtils();

        @Override
        public void pause() {}

        @Override
        public void resume() {}

    }

}
