package mapper;

import pojo.ChatMessage;

import java.util.List;

public interface ChatMapper {

    void record(ChatMessage message);

    List<ChatMessage> getRecords(Integer currentIndex, Integer max);

    Integer getRecordCount();

}
