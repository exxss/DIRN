package com.dobysh.taskmanager.constants;

import java.util.List;

public interface SecurityConstants {
    
    List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
                                                "/js/**",
                                                "/css/**",
                                                "/webjars/bootstrap/5.0.2/**");

    List<String> PROJECTS_PERMISSION_LIST = List.of("/projects/**");
    
    List<String> TASKS_PERMISSION_LIST = List.of("/tasks/**");
    
    List<String> USERS_WHITE_LIST = List.of("/login",
                                            "/users/registration",
                                            "/users/remember-password",
                                            "/users/change-password");
    List<String> ADMIN_PERMISSON_LIST = List.of("/users/list",
                                                "/users/search",
                                                "/users/delete",
                                                "/users/restore");


}
