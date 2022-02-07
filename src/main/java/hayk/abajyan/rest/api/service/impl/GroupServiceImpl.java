package hayk.abajyan.rest.api.service.impl;

import hayk.abajyan.rest.api.model.Group;
import hayk.abajyan.rest.api.repository.GroupRepository;
import hayk.abajyan.rest.api.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Override
    public Optional<Group> get(int id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(int id) {
        groupRepository.deleteById(id);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }
}
