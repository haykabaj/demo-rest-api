package hayk.abajyan.rest.api.controller;

import hayk.abajyan.rest.api.model.RequestEntity;
import hayk.abajyan.rest.api.service.RequestEntityService;
import hayk.abajyan.rest.api.service.impl.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/search/v1/api")
public class RequestEntityController {

    private final HelperService helperService;
    private final RequestEntityService entityService;
    private static final Logger logger = LoggerFactory.getLogger(RequestEntityController.class);


    @Autowired
    public RequestEntityController(HelperService helperService, RequestEntityService entityService) {
        this.helperService = helperService;
        this.entityService = entityService;
    }

    @PostMapping("/entity")
    public ResponseEntity<?> saveEntity(@RequestParam String groupName, @RequestParam int userId){
        Optional<RequestEntity> requestEntity = helperService.getEntityByCriteria(userId, groupName);
        if (!requestEntity.isPresent()){
            logger.warn("maybe you are not in this group or entered incorrect group name");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        entityService.save(requestEntity.get());
        logger.info("request successfully saved");
      return  ResponseEntity.ok(requestEntity.get());
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getGroups(@RequestParam Integer size){
        List<String> groups = entityService.getAll(size);
        if (groups.size() == 0){
            logger.warn("there is no any group");
            return  ResponseEntity.ok(Collections.EMPTY_LIST + " there is no any saved group");
        }
        return ResponseEntity.ok(groups);
    }
}
