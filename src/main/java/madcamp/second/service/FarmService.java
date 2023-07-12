package madcamp.second.service;

import madcamp.second.model.Farm;
import madcamp.second.model.User;

import java.util.List;

public interface FarmService {
    List<Farm> getFarmList();

    List<User> getUserListWithFarm(Long farmId);
}
