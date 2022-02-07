package hayk.abajyan.rest.api.service.impl;

import hayk.abajyan.rest.api.model.RequestEntity;
import hayk.abajyan.rest.api.repository.RequestEntityRepository;
import hayk.abajyan.rest.api.service.RequestEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestEntityServiceImpl implements RequestEntityService {
    private final RequestEntityRepository requestEntityRepository;

    @Autowired
    public RequestEntityServiceImpl(RequestEntityRepository requestEntityRepository) {
        this.requestEntityRepository = requestEntityRepository;
    }

    @Override
    public Optional<RequestEntity> get(int id) {
        return requestEntityRepository.findById(id);
    }

    @Override
    public RequestEntity save(RequestEntity requestEntity) {
        return requestEntityRepository.save(requestEntity);
    }

    @Override
    public void delete(int id) {
        requestEntityRepository.deleteById(id);
    }

    @Override
    public List<String> getAll(Integer size) {
        return requestEntityRepository.getAll(size);
    }
}
