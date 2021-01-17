package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.EventManager;
import dev.minecode.core.api.object.CoreEvent;
import dev.minecode.core.api.object.CoreEventHandler;
import dev.minecode.core.api.object.CoreListener;
import dev.minecode.core.api.object.ListenerObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManagerProvider implements EventManager {

    private static ArrayList<CoreListener> coreListeners = coreListeners = new ArrayList<>();

    public EventManagerProvider() {
    }

    @Override
    public void registerListener(CoreListener listener) {
        coreListeners.add(listener);
    }

    @Override
    public void unregisterListener(CoreListener listener) {
        coreListeners.remove(listener);
    }

    @Override
    public <T extends CoreEvent> T callEvent(T coreEvent) {
        ArrayList<ListenerObject> listenerObjects = new ArrayList<>();
        for (CoreListener listener : coreListeners) {
            for (int i = 0; i < listener.getClass().getDeclaredMethods().length; i++) {
                Method method = listener.getClass().getDeclaredMethods()[i];
                for (int j = 0; j < method.getAnnotations().length; j++) {
                    Annotation annotation = method.getAnnotations()[j];
                    if (annotation.annotationType() == CoreEventHandler.class) {
                        CoreEventHandler coreEventHandler = (CoreEventHandler) annotation;
                        listenerObjects.add(new ListenerObject(listener, method, coreEventHandler.eventPriority().getPriorityInt()));
                    }
                }
            }
        }

        for (int i = 2; i <= 0; i--)
            callEventsByPriority(listenerObjects, i);
        return coreEvent;
    }

    private void callEventsByPriority(ArrayList<ListenerObject> listenerObjects, int priority) {
        for (ListenerObject listenerObject : listenerObjects) {
            if (listenerObject.getEventPriority() == priority) {
                try {
                    listenerObject.getMethod().invoke(listenerObject.getCoreListener());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static ArrayList<CoreListener> getCoreListeners() {
        return coreListeners;
    }
}
