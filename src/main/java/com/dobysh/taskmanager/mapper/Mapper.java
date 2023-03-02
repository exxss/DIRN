package com.dobysh.taskmanager.mapper;



import com.dobysh.taskmanager.dto.GenericDTO;
import com.dobysh.taskmanager.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    D toDto(E entity);

    List<D> toDTOs(List<E> entities);
}

