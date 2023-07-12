package madcamp.second.service;

import madcamp.second.model.Paper;

import java.util.List;

public interface PaperService {
    List<Paper> getPaperWithFarm(Long farmId);

    Paper getPaperById(Long id);

}
