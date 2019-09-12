package service;

import pojo.Message;
import pojo.Topic;

import java.util.List;

public interface ChatRoomSystemService {

    List<Integer> getRoomMembersId(Integer roomNumber);

    List<Message> getRecordByRoomId(Integer id);

    List<Message> getRequestByUserId(Integer id);

    int insertRecord(Message message);

    int insertRequest(Message message);

    Integer getRoomId(Integer fId, Integer sId);

    List<Topic> getTopicByHobby(Integer[] hobbyId);

    int handleRequest(Message message);

    Message getRequest(Message message);

}
