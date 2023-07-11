package madcamp.second.mapper;

import madcamp.second.model.Letter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LetterMapper {
    List<Letter> getLettersBySender(Long senderId);
    List<Letter> getLettersByReceiver(Long receiverId);
    Letter getLetterById(Long id);
    void createLetter(Letter letter);
    void updateLetter(Letter letter);
    void withdrawLetter(Long id);
}
