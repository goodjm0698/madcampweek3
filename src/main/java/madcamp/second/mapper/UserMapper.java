package madcamp.second.mapper;

import madcamp.second.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(Long id);
    List<User> getUserList();
    User getUserByEmail(String email);
    void updateUser(User user);
    void withdrawUser(Long id);
    void enrollUser(User user);
}