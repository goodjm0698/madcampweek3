package madcamp.second.serviceImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import madcamp.second.mapper.LetterMapper;
import madcamp.second.model.Letter;
import madcamp.second.security.JwtTokenUtil;
import madcamp.second.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class LetterServiceImp implements LetterService {
    @Autowired
    LetterMapper letterMapper;

    @Override
    public List<Letter> getLettersBySender(Long senderId) {
        return letterMapper.getLettersBySender(senderId);
    }

    @Override
    public List<Letter> getLettersByReceiver(Long senderId,String writtenDate) {

        return letterMapper.getLettersByReceiver(senderId,writtenDate);
    }

    @Override
    public void createLetter(Letter letter) {

        letterMapper.createLetter(letter);
    }

    @Override
    public Letter getLetterById(Long id)
    {

        return letterMapper.getLetterById(id);
    }

//    @Override
//    public void updateLetter(Letter letter) {
//        letterMapper.updateLetter(letter);
//    }
//
//    @Override
//    public void withdrawLetter(Long id) {
//        letterMapper.withdrawLetter(id);
//    }
}
