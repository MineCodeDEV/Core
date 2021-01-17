package dev.minecode.core.api.object;

public enum CoreEventPriority {

    LOW(0), NORMAL(1), HIGH(2);

    private int priorityInt;

    CoreEventPriority(int priorityInt) {
        this.priorityInt = priorityInt;
    }

    public int getPriorityInt() {
        return priorityInt;
    }
}
