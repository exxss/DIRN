package com.dobysh.taskmanager.constants;

public interface Errors {
    class Books {
        public static final String BOOK_DELETE_ERROR = "Книга не может быть удалена, так как у нее есть активные аренды";
    }

    class Projects{
        public static final String PROJECT_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о проекте";
    }

    class Users{
        public static final String USER_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о пользователе";
    }
}
