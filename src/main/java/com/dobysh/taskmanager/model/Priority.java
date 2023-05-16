package com.dobysh.taskmanager.model;

public enum Priority {
    PRIORITY_1("Приоритет 1"),
    PRIORITY_2("Приоритет 2"),
    PRIORITY_3("Приоритет 3"),
    PRIORITY_4("Приоритет 4");

    private final String priorityTextDisplay;

    Priority(String text) {
        this.priorityTextDisplay = text;
    }

    public String getPriorityTextDisplay() {
        return this.priorityTextDisplay;
    }
}
