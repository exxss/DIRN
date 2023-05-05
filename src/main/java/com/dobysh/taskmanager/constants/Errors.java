package com.dobysh.taskmanager.constants;

public interface Errors {
    class Tasks {
        public static final String TASK_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о задаче";
    }

    class Projects{
        public static final String PROJECT_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о проекте";
    }

    class Users{
        public static final String USER_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о пользователе";
    }
}
