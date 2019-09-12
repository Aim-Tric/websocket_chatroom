package service.impl;

import mapper.ChatMapper;
import mapper.HobbyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Message;
import pojo.Topic;
import service.ChatRoomSystemService;

import java.util.List;

@Service
public class ChatRoomSystemServiceImpl implements ChatRoomSystemService {

    @Autowired
    ChatMapper chatMapper;

    @Autowired
    HobbyMapper hobbyMapper;

    @Override
    public List<Integer> getRoomMembersId(Integer roomNumber) {
        return chatMapper.getRoomMembersId(roomNumber);
    }

    @Override
    public List<Message> getRecordByRoomId(Integer id) {
        return chatMapper.getRecordByRoomId(id);
    }

    @Override
    public List<Message> getRequestByUserId(Integer id) {
        return chatMapper.getRequestByUserId(id);
    }

    @Override
    public int insertRecord(Message message) {
        return chatMapper.insertRecord(message);
    }

    @Override
    public int insertRequest(Message message) {
        return chatMapper.insertRequest(message);
    }

    @Override
    public Integer getRoomId(Integer fId, Integer sId) {
        return chatMapper.getRoomId(fId, sId);
    }

    @Override
    public List<Topic> getTopicByHobby(Integer[] hobbyId) {
        return hobbyMapper.getTopicByHobby(hobbyId);
    }

    @Override
    public int handleRequest(Message message) {
        return chatMapper.handleRequest(message);
    }

    @Override
    public Message getRequest(Message message) {
        return chatMapper.getRequest(message);
    }

}
