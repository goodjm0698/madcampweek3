package madcamp.second.mapper;

import madcamp.second.model.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperMapper {
    Paper getPaperById(Long id);

    List<Paper> getPaperListWithFarm(Long farmId);
}
