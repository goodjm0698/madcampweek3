package madcamp.second.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import madcamp.second.model.Letter;
import madcamp.second.service.LetterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class LetterController
{
    @Autowired
    LetterService letterService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/sent_letters")
    public ResponseEntity<String> sentLetters() throws JsonProcessingException {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Letter> letters = letterService.getLettersBySender(id);
        String json = objectMapper.writeValueAsString(letters);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/received_letters")
    public ResponseEntity<String> receivedLetters() throws JsonProcessingException {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Letter> letters = letterService.getLettersByReceiver(id);
        String json = objectMapper.writeValueAsString(letters);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/update_letter/{id}")
    public ResponseEntity<String> updateLetter(@PathVariable("id") Long id, @RequestBody Letter letter) throws Exception
    {
        Letter origin = letterService.getLetterById(id);
        origin.setText(letter.getText());
        origin.setIsAno(letter.getIsAno());

        letterService.updateLetter(origin);
        String json = objectMapper.writeValueAsString(origin);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/delete_letter/{id}")
    public ResponseEntity<String> withdrawLetter(@PathVariable("id") Long id)
    {
        letterService.withdrawLetter(id);
        String json = "deleted successfully";
        return ResponseEntity.ok(json);
    }
}
