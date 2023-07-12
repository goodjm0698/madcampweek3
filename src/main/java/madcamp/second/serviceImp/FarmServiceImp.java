package madcamp.second.serviceImp;

import madcamp.second.mapper.FarmMapper;
import madcamp.second.model.Farm;
import madcamp.second.model.User;
import madcamp.second.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImp implements FarmService {
    @Autowired
    FarmMapper farmMapper;

    @Override
    public List<Farm> getFarmList()
    {
        return farmMapper.getFarmList();
    }

    @Override
    public List<User> getUserListWithFarm(Long farmId)
    {
        return farmMapper.getUserListWithFarm(farmId);
    }
}
