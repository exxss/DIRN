package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.GenericDTO;
import com.dobysh.taskmanager.mapper.GenericMapper;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.repository.GenericRepository;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class GenericTest<E extends GenericModel,D extends GenericDTO> {
    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(CustomUserDetails
                .builder()
                .username("USER"),
                null,
                null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected abstract void getAll();

    protected abstract void getOne();

    protected abstract void create();

    protected abstract void update();

    protected abstract void delete();


}
