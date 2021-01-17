package dev.minecode.core.api.object;

import java.lang.reflect.Method;

public class ListenerObject {

    private CoreListener coreListener;
    private Method method;
    private int eventPriority;

    public ListenerObject(CoreListener coreListener, Method method, int eventPriority) {
        this.coreListener = coreListener;
        this.method = method;
        this.eventPriority = eventPriority;
    }

    public CoreListener getCoreListener() {
        return coreListener;
    }

    public Method getMethod() {
        return method;
    }

    public int getEventPriority() {
        return eventPriority;
    }
}
