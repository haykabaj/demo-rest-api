package hayk.abajyan.rest.api.repository;

import hayk.abajyan.rest.api.model.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestEntityRepository extends JpaRepository<RequestEntity, Integer> {

    @Query(value = "SELECT requests.group_name FROM requests limit ?1", nativeQuery = true)
    List<String> getAll(Integer size);
}
