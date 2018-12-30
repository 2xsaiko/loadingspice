package therealfarfetchd.loadingspice.api;

public interface LoadingProgress {

    LoadingProgress INSTANCE = APIInternals.getLoadingProgress();

    TaskInfo pushTask();

    void popTask(TaskInfo ti);

    void popTask();

    interface TaskInfo {

        TaskInfo withTaskName(String name);

    }

}
