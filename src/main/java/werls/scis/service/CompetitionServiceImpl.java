package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.CompetitionRepository;
import werls.scis.dao.pojo.ScisCompetition;

import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 22:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompetitionServiceImpl {
    @Autowired
    private CompetitionRepository competitionRepository;

    /**
     * 模糊查询竞赛名称
     *
     * @param name     String
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    public Page<ScisCompetition> findByNameLike(String name, Pageable pageable) {
        return competitionRepository.findByNameContaining(name, pageable);
    }

    /**
     * 竞赛状态
     *
     * @param status   String 状态
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    public Page<ScisCompetition> findByStatus(String status, Pageable pageable) {
        return competitionRepository.findByStatus(status, pageable);
    }
    /**
     * 按照发布者模糊查询
     * @param authorLike String 发布者
     * @param pageable Pageable
     * @return  Page<ScisCompetition>
     */
    public Page<ScisCompetition> findByAuthorLike(String authorLike,Pageable pageable){
        return competitionRepository.findByAuthorLike(authorLike, pageable);
    }
    /**
     * 竞赛级别
     * @param level String 竞赛级别
     * @param pageable Pageable
     * @return  Page<ScisCompetition>
     */
    public Page<ScisCompetition> findByLevel(String level, Pageable pageable){
        return competitionRepository.findByLevel(level, pageable);
    }
    /**
     * 举办单位
     * @param organizer String 举办单位
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    public Page<ScisCompetition> findByOrganizer(String organizer, Pageable pageable){
        return competitionRepository.findByOrganizer(organizer, pageable);
    }

    public void save(ScisCompetition scisCompetition){
        competitionRepository.save(scisCompetition);
    }
    public void saveAll(List<ScisCompetition> scisCompetition){
        competitionRepository.saveAll(scisCompetition);
    }
    public Page<ScisCompetition> findAll(Pageable pageable){
        return competitionRepository.findAll(pageable);
    }
    public void delete(ScisCompetition scisCompetition){
        this.competitionRepository.delete(scisCompetition);
    }
    public void deleteAll(List<ScisCompetition> scisCompetition){
        this.competitionRepository.deleteAll(scisCompetition);
    }
    public void deleteById(Integer id){
        this.competitionRepository.deleteById(id);
    }
    public Optional<ScisCompetition> findById(Integer id){
        return  competitionRepository.findById(id);
    }
    /**
     * 通过名称和举办单位查询
     * @param name String 赛事名称
     * @param organizer String 举办单位
     * @param pageable  Pageable
     * @return  Page<ScisCompetition>
     */
    public  Page<ScisCompetition> findByNameLikeAndOrganizer(String name,String organizer,Pageable pageable){
        return competitionRepository.findByNameLikeAndOrganizer(name,organizer,pageable);
    }

}
