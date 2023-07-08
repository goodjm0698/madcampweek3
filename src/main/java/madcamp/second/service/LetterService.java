package madcamp.second.service;

import madcamp.second.model.Letter;

import java.util.List;

public interface LetterService {
    List<Letter> getLettersBySender(Long senderId);
    List<Letter> getLettersByReceiver(Long receiverId);
    void createLetter(Letter letter);
    void updateLetter(Letter letter);
    void withdrawLetter(Long id);
    Letter getLetterById(Long id);
}
