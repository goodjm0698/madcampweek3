package madcamp.second.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.oauth2.sdk.Response;
import madcamp.second.model.Letter;
import madcamp.second.model.LetterForm;
import madcamp.second.security.JwtTokenUtil;
import madcamp.second.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5000")
public class LetterController
{
    @Autowired
    LetterService letterService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @PostMapping("/sent_letters")
    public ResponseEntity<String> sentLetters(@RequestHeader("Authorization") String token, @RequestBody LetterForm body) throws JsonProcessingException {
        try
        {
            Long senderId = jwtTokenUtil.extractUserId(token.substring(7));
            Letter letter = new Letter();
            letter.setIsAno(0);
            letter.setText(body.getText());
            letter.setPosX(body.getPosX());
            letter.setPosY(body.getPosY());
            letter.setImgType((int)(Math.random()*14));
            letter.setReceiverId(body.getReceiverId());
            letter.setSenderId(senderId);
            letter.setGeneratedDate(LocalDate.now());
            letter.setOpenDate(LocalDate.now());
            letterService.createLetter(letter);

            return ResponseEntity.ok("Success!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("create letter failed");
    }

    @GetMapping("/received_letters")
    public ResponseEntity<String> receivedLetters(@RequestHeader("Authorization") String token, @RequestParam(value = "id", required = false) Long retrieveId) throws JsonProcessingException {
        try
        {
            Long receiverId = jwtTokenUtil.extractUserId(token.substring(7));
            List<Letter> letters;
            if(retrieveId == null)
            {
                letters = letterService.getLettersByReceiver(receiverId);
            }
            else
            {
                letters = letterService.getLettersByReceiver(retrieveId);
            }

            String json = objectMapper.writeValueAsString(letters);
            return ResponseEntity.ok(json);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("request failed");
    }

    @GetMapping("/letter")
    public ResponseEntity<String> getLetter(@RequestHeader("Authorization") String token, @RequestParam Long id)
    {
        try
        {
            Long userId = jwtTokenUtil.extractUserId(token.substring(7));

            Letter letter = letterService.getLetterById(id);

            System.out.println("yeonuk's receiverid and userid is : "+letter.getReceiverId()+", "+userId);

//            if(userId != letter.getReceiverId())
//            {
//                throw new BadCredentialsException("You are not receiver");
//            }

            String json = objectMapper.writeValueAsString(letter);
            return ResponseEntity.ok(json);
        }
        catch(Exception e)
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
