package therealfarfetchd.loadingspice;

import java.util.concurrent.atomic.AtomicInteger;

public class NetTracker {

    public static final AtomicInteger rx = new AtomicInteger(0);
    public static final AtomicInteger tx = new AtomicInteger(0);

}
