package hayk.abajyan.rest.api.controller;


import hayk.abajyan.rest.api.model.Group;
import hayk.abajyan.rest.api.service.impl.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;

@Controller
@RequestMapping("/search/v1/api")
public class GroupController {


    private final HelperService helperService;

    @Autowired
    public GroupController(HelperService helperService) {
        this.helperService = helperService;
    }

    @GetMapping(value = "/group")
    public ResponseEntity<Group> getEntity(@RequestParam String groupName, @RequestParam int userId) {
        Optional<Group> findGroup = helperService.getGroupByCriteria(userId, groupName);
        return findGroup.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }


}
