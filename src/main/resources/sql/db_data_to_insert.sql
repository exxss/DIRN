-- ROLES
insert into roles
values (1, 'Роль админа', 'ADMIN'),
       (2, 'Роль позьзователя', 'USER');
-- USERS
insert into users
values (nextval('users_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,null,'kirill@mail.ru','Kirill','Petrov','krpetrov','$2a$10$naW928i1KBRbDNfBaH12BeAU6CC6xb6dXGLdFT3bZeszmXCeWa9Ta',1);
insert into users
values (nextval('users_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,null,'dobysh.n@yandex.ru','Nikita','Dobysh','dobysh.n','$2a$10$naW928i1KBRbDNfBaH12BeAU6CC6xb6dXGLdFT3bZeszmXCeWa9Ta',1);
-- PROJECTS
insert into projects
values (nextval('projects_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Для учёбы');
insert into projects
values (nextval('projects_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Для проектов');
insert into projects
values (nextval('projects_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Для жизни');
insert into projects
values (nextval('projects_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Для домашних дел');
-- TASKS
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Сделать дз по англ',0,'ДЗ');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Сделать дз по java',0,'ДЗ');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'Выучить вопросы по java',0,'вопросы java');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'сделать mappers and dto',0,'дипломный проект');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'добавить страницы',0,'дипломный проект');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'сделать dto в pet project',0,'dto pet project');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'сделать контроллеры',0,'controllers pet project');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'молоко сыр яйца',0,'съездить за продуктами');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'',0,'помыть полы');
insert into tasks
values (nextval('tasks_seq'),'INSERT-DB','2022-11-15 13:46:11.797607',null,null,false,'',0,'постирать вещи');
-- USER-PROJECT
insert into users_projects
values (1,1);
insert into users_projects
values (1,2);
insert into users_projects
values (2,3);
insert into users_projects
values (2,4);
-- USER-TASKS
insert into users_tasks
values (1,1);
insert into users_tasks
values (1,2);
insert into users_tasks
values (1,3);
insert into users_tasks
values (1,4);
insert into users_tasks
values (1,5);
insert into users_tasks
values (2,6);
insert into users_tasks
values (2,7);
insert into users_tasks
values (2,8);
insert into users_tasks
values (2,9);
insert into users_tasks
values (2,10);
-- TASK-PROJECT
insert into tasks_projects
values (1,1);
insert into tasks_projects
values (1,2);
insert into tasks_projects
values (1,3);
insert into tasks_projects
values (2,4);
insert into tasks_projects
values (3,5);
insert into tasks_projects
values (3,6);
insert into tasks_projects
values (3,7);
insert into tasks_projects
values (4,8);
insert into tasks_projects
values (4,9);
insert into tasks_projects
values (4,10);
