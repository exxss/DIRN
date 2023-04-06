package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.GenericDTO;
import com.dobysh.taskmanager.exception.MyDeleteException;
import com.dobysh.taskmanager.mapper.GenericMapper;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
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
        newObject.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDto(repository.save(mapper.toEntity(newObject)));
    }

    public N update(N object) {
        log.info(object.toString());
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    public void delete(final Long id) throws MyDeleteException {
        repository.deleteById(id);
    }

    public void markAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(true);
        genericModel.setDeletedWhen(LocalDateTime.now());
        genericModel.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void unMarkAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(false);
        genericModel.setDeletedWhen(null);
        genericModel.setDeletedBy(null);
    }
}


