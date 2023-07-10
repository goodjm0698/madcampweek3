package madcamp.second.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.Response;
import madcamp.second.model.Letter;
import madcamp.second.security.JwtTokenUtil;
import madcamp.second.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5000")
public class LetterController
{
    @Autowired
    LetterService letterService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/sent_letters")
    public ResponseEntity<String> sentLetters() throws JsonProcessingException {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Letter> letters = letterService.getLettersBySender(id);
        String json = objectMapper.writeValueAsString(letters);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/received_letters")
    public ResponseEntity<String> receivedLetters(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        try
        {
            String userId = jwtTokenUtil.extractUserId(token.substring(7));
            Long receiverId = Long.parseLong(userId);
            List<Letter> letters = letterService.getLettersByReceiver(receiverId);
            String json = objectMapper.writeValueAsString(letters);
            return ResponseEntity.ok(json);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("request failed");
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
