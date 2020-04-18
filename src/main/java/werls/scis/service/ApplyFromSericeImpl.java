package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.ApplyFromRepository;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisApplyFrom;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 15:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyFromSericeImpl {
    @Autowired
    private ApplyFromRepository apply;

    /**
     * 用户id
     *
     * @param id ApplyId Integer
     * @return Map<String, Object> userId ,integer 用户id
     */
    public Map<String, Object> findUserIdByApplyId(Integer id) {
        return apply.findUserIdByApplyId(id);
    }

    public ScisApplyFrom findByUserIdCompetitionId(Integer userId, Integer competitionId) {
        return apply.findByUserIdCompetitionId(userId, competitionId);
    }

    public List<Map<String, Object>> findScisUserIdT(Integer id, String name, Integer page, Integer size) {
        return apply.findScisUserIdT(id, name, page, size);
    }

    public List<Map<String, Object>> findScisUserIdA(Integer id, String name, Integer page, Integer size) {
        return apply.findScisUserIdA(id, name, page, size);
    }

    public Map<String, Object> findScisUserIdA(Integer id, String name) {
        return apply.findScisUserIdA(id, name);
    }

    public List<Map<String, Object>> findScisUserIdA(Integer id) {
        return apply.findScisUserIdA(id);
    }

    public Map<String, Object> findScisUserIdB(Integer id) {
        return apply.findScisUserIdB(id);
    }

    public void update(Integer userId, Integer competitionId, Integer worksId) {
        apply.update(userId, competitionId, worksId);
    }

    public Page<ScisApplyFrom> findAllByCompetitionId(Integer id, Pageable pageable) {
        return apply.findAllByCompetitionId(id, pageable);
    }

    public List<Map<String, Object>> findCompetitionId(Integer id, String name, Integer page, Integer size) {
        return apply.findCompetitionId(id, name, page, size);
    }

    public List<Map<String, Object>> findCompetitionId(Integer id) {
        return apply.findCompetitionId(id);
    }

    public List<ScisApplyFrom> findAllByCompetitionId(Integer id) {
        return apply.findAllByCompetitionId(id);
    }

    public List<Map<String, Object>> findGrades(Integer competitionId, String userName, Integer page, Integer size) {
        return apply.findGrades(competitionId, userName, page, size);
    }

    public Map<String, Object> findGrades(String userName, Integer competitionId) {
        return apply.findGrades(userName, competitionId);
    }

    /**
     * 报名状态 分页 排序
     *
     * @param status   状态
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */

    public Page<ScisApplyFrom> findByStatus(String status, Pageable pageable) {
        return apply.findByStatus(status, pageable);
    }

    public Optional<ScisApplyFrom> findById(Integer id) {
        return apply.findById(id);
    }

    public ScisApplyFrom save(ScisApplyFrom applyFrom) {
        return apply.save(applyFrom);
    }

    public List<ScisApplyFrom> save(List<ScisApplyFrom> applyFrom) {
        return apply.saveAll(applyFrom);
    }

    public Page<ScisApplyFrom> findAll(Pageable pageable) {
        return apply.findAll(pageable);
    }

    public void delete(ScisApplyFrom applyFrom) {
        this.apply.delete(applyFrom);
    }

    public void deleteAll(List<ScisApplyFrom> applyFrom) {
        this.apply.deleteAll(applyFrom);
    }

    public void deleteById(Integer id) {
        this.apply.deleteById(id);
    }

}
