package com.bajaj.webhookapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class MutualFollowers {

    //Question 1: Mutual Followers
    //Identify mutual follow pairs where both users follow each other. Output only direct 2-node
    //cycles as [min, max] once.

    public static void main(String[] args) {
        String inputJson = "{\n" +
                "  \"users\": [\n" +
                "    {\"id\": 1, \"name\": \"Alice\", \"follows\": [2, 3]},\n" +
                "    {\"id\": 2, \"name\": \"Bob\", \"follows\": [1]},\n" +
                "    {\"id\": 3, \"name\": \"Charlie\", \"follows\": [4]},\n" +
                "    {\"id\": 4, \"name\": \"David\", \"follows\": [3]}\n" +
                "  ]\n" +
                "}";


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputJson);
            JsonNode usersNode = rootNode.path("users");


            Map<Integer, Set<Integer>> followsMap = new HashMap<>();


            for (JsonNode userNode : usersNode) {
                int userId = userNode.path("id").asInt();
                Set<Integer> follows = new HashSet<>();
                for (JsonNode followNode : userNode.path("follows")) {
                    follows.add(followNode.asInt());
                }
                followsMap.put(userId, follows);
            }

            // set for unique pairs
            Set<List<Integer>> mutualFollowers = new HashSet<>();

            // Finding mutual follow pairs here
            for (Map.Entry<Integer, Set<Integer>> entry : followsMap.entrySet()) {
                int userId = entry.getKey();
                Set<Integer> follows = entry.getValue();

                for (Integer followId : follows) {
                    //if followId follows userId back
                    if (followsMap.containsKey(followId) && followsMap.get(followId).contains(userId)) {
                        // Add the mutual pair to the Set in
                        mutualFollowers.add(Arrays.asList(Math.min(userId, followId), Math.max(userId, followId)));
                    }
                }
            }

            Map<String, Object> output = new HashMap<>();
            output.put("regNo", "REG12347");
            output.put("outcome", new ArrayList<>(mutualFollowers));


            String resultJson = objectMapper.writeValueAsString(output);
            System.out.println(resultJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}