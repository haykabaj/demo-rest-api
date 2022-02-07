package hayk.abajyan.rest.api.service;

import hayk.abajyan.rest.api.model.RequestEntity;

import java.util.List;
import java.util.Optional;

public interface RequestEntityService {

    Optional<RequestEntity> get(int id);
    RequestEntity save(RequestEntity requestEntity);
    void delete(int id);
    List<String> getAll(Integer size);
}
