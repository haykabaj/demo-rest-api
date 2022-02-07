package hayk.abajyan.rest.api.service;

import hayk.abajyan.rest.api.model.Group;
import hayk.abajyan.rest.api.model.RequestEntity;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Optional<Group> get(int id);
    Group save(Group group);
    void delete(int id);
    List<Group> getAll();
}
