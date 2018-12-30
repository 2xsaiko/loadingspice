package therealfarfetchd.loadingspice;

import java.util.ArrayList;
import java.util.List;

import therealfarfetchd.loadingspice.api.LoadingProgress;

public class LoadingProgressImpl implements LoadingProgress {

    public static final LoadingProgressImpl INSTANCE = new LoadingProgressImpl();

    private LoadingProgressImpl() {}

    /* @Nullable */ private TaskInfoImpl current = null;

    @Override
    public TaskInfo pushTask() {
        current = new TaskInfoImpl(current);
        return current;
    }

    @Override
    public void popTask(TaskInfo ti) {
        while (current != null) {
            TaskInfoImpl lastCurrent = current;
            current = current.parent;
            if (lastCurrent == ti) break;
        }
    }

    @Override
    public void popTask() {
        popTask(current);
    }

    public TaskInfoImpl getCurrentTask() {
        return current;
    }

    public static class TaskInfoImpl implements TaskInfo {

        private String name = "Loading...";

        /* @Nullable */ private TaskInfoImpl parent;

        private List<TaskInfoImpl> children = new ArrayList<>();

        private TaskInfoImpl(/* @Nullable */ TaskInfoImpl parent) {
            this.parent = parent;

            if (parent != null) {
                parent.children.add(this);
            }
        }

        @Override
        public TaskInfo withTaskName(String name) {
            this.name = name;
            return this;
        }

        public String getTaskText() {
            return name;
        }

    }

}
