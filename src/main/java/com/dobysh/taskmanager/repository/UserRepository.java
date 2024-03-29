package com.dobysh.taskmanager.repository;

import com.dobysh.taskmanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User> {

    User findUserByLogin(String login);

    User findUserByLoginAndIsDeletedFalse(String login);

    User findUserByEmail(String email);

    User findUserByChangePasswordToken(String token);

    @Query(nativeQuery = true,
            value = """
                 select u.*
                 from users u
                 where u.first_name ilike '%' || coalesce(:firstName, '%') || '%'
                 and u.last_name ilike '%' || coalesce(:lastName, '%') || '%'
                 and u.login ilike '%' || coalesce(:login, '%') || '%'
                  """)
    Page<User> searchUsers(String firstName,
                           String lastName,
                           String login,
                           Pageable pageable);
    @Query(nativeQuery = true,
            value = """
                select distinct email
                from  users u left join users_tasks on u.id = users_tasks.user_id
                              left join tasks t on t.id = users_tasks.task_id
                where t.expiration_date <= now() and u.is_deleted = false
                 """)
    List<String> getDelayedEmails();

}
