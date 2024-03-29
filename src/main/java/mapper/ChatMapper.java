package mapper;

import pojo.HobbyTag;
import pojo.Message;
import pojo.Topic;

import java.util.List;

public interface ChatMapper {

    List<Integer> getRoomMembersId(Integer roomNumber);

    List<Message> getRecordByRoomId(Integer id);

    List<Message> getRequestByUserId(Integer id);

    int insertRecord(Message message);

    int insertRequest(Message message);

    Integer getRoomId(Integer fId, Integer sId);

    int handleRequest(Message message);

    Message getRequest(Message message);

}
