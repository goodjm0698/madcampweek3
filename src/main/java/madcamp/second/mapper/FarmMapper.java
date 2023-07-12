package madcamp.second.mapper;

import madcamp.second.model.Farm;
import madcamp.second.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FarmMapper {
    List<Farm> getFarmList();

    List<User> getUserListWithFarm(Long farmId);
}
