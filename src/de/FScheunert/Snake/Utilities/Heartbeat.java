package de.FScheunert.Snake.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Heartbeat implements Runnable {

    private static final HashMap<String, Heartbeat> threadMap = new HashMap<>();

    private final Object instance;
    private final String method;
    private final long delay;
    private final Thread thread;

    private boolean suspended = false;

    public Heartbeat(Object instance, String method, long delay) {
        this.instance = instance;
        this.method = method;
        this.delay = delay;
        thread = new Thread(this);
        thread.start();
        threadMap.put(method.toUpperCase(), this);
    }

    public Heartbeat suspend() {
        this.suspended = true;
        return this;
    }

    private void heartBeat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.instance.getClass().getMethod(this.method).invoke(this.instance);
    }

    public Thread getThread() {
        return this.thread;
    }

    @Override
    public void run() {
        while(!suspended) {
            try {
                heartBeat();
                Thread.sleep(this.delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
