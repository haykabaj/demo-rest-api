package hayk.abajyan.rest.api.service.impl;

import hayk.abajyan.rest.api.model.Group;
import hayk.abajyan.rest.api.model.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.jfunc.json.JsonArray;
import top.jfunc.json.JsonObject;
import top.jfunc.json.impl.JSONObject;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class  HelperService {

    private static final Logger logger = LoggerFactory.getLogger(HelperService.class);

    private final RestTemplate restTemplate;
    @Value("${vkApiVersion}")
    private String vkApiVersion;

    @Autowired
    public HelperService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JsonObject getGroup(String groupName) {
        String searchGroupUrl = "https://api.vk.com/method/groups.search?q=" + groupName + this.vkApiVersion;
        ResponseEntity<String> response = response(searchGroupUrl);
        JSONObject json = new JSONObject(response.getBody());
        JsonObject responseJson = json.getJsonObject("response");
        JsonArray items = responseJson.getJsonArray("items");
        return items.getJsonObject(0);
    }

    public List<Integer> getUserFollowers(int userId) {
        String searchFollowersUrl = "https://api.vk.com/method/friends.get?user_id=" + userId + this.vkApiVersion;
        ResponseEntity<String> response = response(searchFollowersUrl);
        JSONObject json = new JSONObject(response.getBody());
        JsonObject responseJson = json.getJsonObject("response");
        JsonArray items = responseJson.getJsonArray("items");
        return IntStream.range(0, items.size()).mapToObj(items::getInteger).collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<Group> getGroupByCriteria(int userId, String groupName) {
        JsonObject groupJson = getGroup(groupName);
        int id = groupJson.getInteger("id");
        String name = (String) groupJson.get("name");
        List<Integer> userFollowers = getUserFollowers(userId);
        List<Integer> groupMembers = getGroupMembers(id);
        userFollowers.add(userId);
        Set<Integer> result = userFollowers.stream()
                .distinct()
                .filter(groupMembers::contains)
                .collect(Collectors.toSet());
        if (result.size() != 0) {
            Group group =  new Group();
            group.setId(id);
            group.setName(name);
            return Optional.of(group);
        }
        logger.info("No relevant information !");
        return Optional.empty();
    }

    public Optional<RequestEntity> getEntityByCriteria(int userId, String groupName) {
        LocalDate now =  LocalDate.now();
        RequestEntity requestEntity;
        JsonObject groupJson = getGroup(groupName);
        int id = groupJson.getInteger("id");
        Set<Integer> result = getGroupMembers(id).stream()
                .distinct()
                .filter(i -> i == userId)
                .collect(Collectors.toSet());
            if (result.size() != 0){
                 requestEntity =  new RequestEntity();
                 requestEntity.setRequestDate(now);
                 requestEntity.setRequestParams(Arrays.asList(groupName, String.valueOf(userId)));
                 requestEntity.setGroupName(groupName);
                 return Optional.of(requestEntity);
            }
            logger.warn("No relevant information");
        return Optional.empty();
    }


    public ArrayList<Integer> getGroupMembers(int groupId) {
        String searchGroupMembersUrl = "https://api.vk.com/method/groups.getMembers?group_id=" + groupId + this.vkApiVersion;
        ResponseEntity<String> response = response(searchGroupMembersUrl);
        JSONObject json = new JSONObject(response.getBody());
        JsonObject responseJson = json.getJsonObject("response");
        JsonArray items = responseJson.getJsonArray("items");
        return IntStream.range(0, items.size()).mapToObj(items::getInteger).collect(Collectors.toCollection(ArrayList::new));
    }

    private ResponseEntity<String> response(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", getBearerTokenHeader());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    private String getBearerTokenHeader() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization");
    }

}
