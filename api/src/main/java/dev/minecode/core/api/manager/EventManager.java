package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CoreEvent;
import dev.minecode.core.api.object.CoreListener;

public interface EventManager {

    void registerListener(CoreListener listener);

    void unregisterListener(CoreListener listener);

    <T extends CoreEvent> T callEvent(T coreEvent);

}
