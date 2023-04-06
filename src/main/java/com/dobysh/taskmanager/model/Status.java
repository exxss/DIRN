package com.dobysh.taskmanager.model;

public enum Status {
    PLANNED("Запланирована"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершена"),
    CANCELED("Отменена");

    private final String statusTextDisplay;

    Status(String text) {
        this.statusTextDisplay = text;
    }

    public String getStatusTextDisplay() {
        return this.statusTextDisplay;
    }
}

