package mapper;

import pojo.HobbyTag;
import pojo.Topic;

import java.util.List;

public interface HobbyMapper {

    List<HobbyTag> getHobbyByUserId(Integer id);

    List<HobbyTag> getHobbyTag();

    List<Topic> getTopicByHobby(Integer[] hobbyId);

    List<Integer> getMembersByHobbyId(Integer[] hobbyId);
}
