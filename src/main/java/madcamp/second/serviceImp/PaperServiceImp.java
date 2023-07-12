package madcamp.second.serviceImp;

import madcamp.second.mapper.PaperMapper;
import madcamp.second.model.Paper;
import madcamp.second.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImp implements PaperService {

    @Autowired
    PaperMapper paperMapper;

    @Override
    public List<Paper> getPaperWithFarm(Long farmId) {
        return paperMapper.getPaperListWithFarm(farmId);
    }

    @Override
    public Paper getPaperById(Long id) {
        return paperMapper.getPaperById(id);
    }
}
