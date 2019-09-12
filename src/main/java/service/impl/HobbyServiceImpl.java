package service.impl;

import mapper.HobbyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.HobbyTag;
import pojo.Topic;
import service.HobbyService;

import java.util.List;

@Service
public class HobbyServiceImpl implements HobbyService {

    @Autowired
    private HobbyMapper hobbyMapper;

    @Override
    public List<HobbyTag> getHobbyTag() {
        return hobbyMapper.getHobbyTag();
    }

    @Override
    public List<Topic> getTopicByHobby(Integer[] hobbyId) {
        return hobbyMapper.getTopicByHobby(hobbyId);
    }

    @Override
    public List<Integer> getMembersByHobbyId(Integer[] hobbyId) {
        return hobbyMapper.getMembersByHobbyId(hobbyId);
    }

    @Override
    public List<HobbyTag> getUserHobby(Integer id) {
        return hobbyMapper.getHobbyByUserId(id);
    }



}
