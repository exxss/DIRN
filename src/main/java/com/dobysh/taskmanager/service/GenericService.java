package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.GenericDTO;
import com.dobysh.taskmanager.mapper.GenericMapper;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.repository.GenericRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {

    protected final GenericRepository<T> repository;
    protected final GenericMapper<T, N> mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericService(GenericRepository<T> repository, GenericMapper<T, N> mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<N> listAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public N getOne(final Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Элемент по этому ID не найден")));
    }

    public N create(N newObject) {
        return mapper.toDto(repository.save(mapper.toEntity(newObject)));
    }

    public N update(N updatedObject) {
        return mapper.toDto(repository.save(mapper.toEntity(updatedObject)));
    }

    public void delete(final Long id) {
        repository.deleteById(id);
    }
}


