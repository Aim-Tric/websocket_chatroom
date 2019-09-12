package service;

import pojo.HobbyTag;
import pojo.Topic;

import java.util.List;

public interface HobbyService {

    List<HobbyTag> getHobbyTag();

    List<Topic> getTopicByHobby(Integer[] hobbyId);

    List<Integer> getMembersByHobbyId(Integer[] hobbyId);

    List<HobbyTag> getUserHobby(Integer id);

}
