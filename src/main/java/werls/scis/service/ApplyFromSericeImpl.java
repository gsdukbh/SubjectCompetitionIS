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

    public List<ScisApplyFrom> findBySocreDesc(Integer competitionId) {
        return apply.findBySocreDesc(competitionId);
    }

    public void update(Integer id, String prize) {
        apply.update(id, prize);
    }

    public void update(List<ScisApplyFrom> id, String prize) {
        for (ScisApplyFrom ids : id) {
            apply.update(ids.getId(), prize);
        }
    }

    public Integer getGradeRank(Integer applyId) {
        return apply.getGradeRank(applyId);
    }

    public int gradeDistribution(Integer competitionId, String prize, String college) {
        return apply.gradeDistribution(competitionId, prize, college);
    }

    public Page<ScisApplyFrom> findByScisUserNameContaining(String name, Pageable pageable) {
        return apply.findByScisUserNameContaining(name, pageable);
    }

    public int gradeDistribution(Integer competitionId, Integer left, Integer right) {
        return apply.gradeDistribution(competitionId, left, right);
    }

    public int gradeDistribution(Integer competitionId, String prize) {
        return apply.gradeDistribution(competitionId, prize);
    }

    public int gradeDistribution(Integer competitionId, Integer left, Integer right, String college) {
        return apply.gradeDistribution(competitionId, left, right, college);
    }

    public Page<ScisApplyFrom> findByScisUserNameContainingOrScisUserLoginContaining(String name, String login, Pageable pageable) {
        return apply.findByScisUserNameContainingOrScisUserLoginContaining(name, login, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserNameContainingOrScisUserLoginContainingAndScoreBetween(
            String name, String login, Integer left, Integer right, Pageable pageable) {
        return apply.findByScisUserNameContainingOrScisUserLoginContainingAndScoreBetween(name, login, left, right, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserNameContainingOrScisUserLoginContainingAndPrize(String name, String login, Integer grade, Pageable pageable) {
        switch (grade) {
            case 4:
                return apply.findByScisUserNameContainingOrScisUserLoginContainingAndPrize(name, login, "一等奖", pageable);
            case 3:
                return apply.findByScisUserNameContainingOrScisUserLoginContainingAndPrize(name, login, "二等奖", pageable);
            case 2:
                return apply.findByScisUserNameContainingOrScisUserLoginContainingAndPrize(name, login, "三等奖", pageable);
            default:
                return null;
        }
    }

    public Page<ScisApplyFrom> findByScisUserIdAndCompetitionIdAndScoreBetween(Integer userId, Integer competitionId, Integer left, Integer right, Pageable pageable) {
        return apply.findByScisUserIdAndCompetitionIdAndScoreBetween(userId, competitionId, left, right, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserIdAndCompetitionIdAndScoreBetweenAndScoreNotNull(Integer userId, Integer competitionId, Integer left, Integer right, Pageable pageable) {
        return apply.findByScisUserIdAndCompetitionIdAndScoreBetweenAndScoreNotNull(userId, competitionId, left, right, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserIdAndCompetitionIdAndPrizeAndScoreNotNull(
            Integer userId, Integer competitionId, Integer grade, Pageable pageable) {
        switch (grade) {
            case 4:
                return apply.findByScisUserIdAndCompetitionIdAndPrizeAndScoreNotNull(userId, competitionId, "一等奖", pageable);
            case 3:
                return apply.findByScisUserIdAndCompetitionIdAndPrizeAndScoreNotNull(userId, competitionId, "二等奖", pageable);
            case 2:
                return apply.findByScisUserIdAndCompetitionIdAndPrizeAndScoreNotNull(userId, competitionId, "三等奖", pageable);
            default:
                return null;
        }
    }

    public Page<ScisApplyFrom> findByScisUserLoginContaining(String login, Pageable pageable) {
        return apply.findByScisUserLoginContaining(login, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserId(Integer id, Pageable pageable) {
        return apply.findByScisUserId(id, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserIdAndScoreNotNull(Integer id, Pageable pageable) {
        return apply.findByScisUserIdAndScoreNotNull(id, pageable);
    }

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

    public Page<ScisApplyFrom> findAllByCompetitionIdAndScisUserId(Integer id, Integer userId, Pageable pageable) {
        return apply.findAllByCompetitionIdAndScisUserId(id, userId, pageable);
    }

    public Page<ScisApplyFrom> findAllByCompetitionIdAndScisUserIdAndScoreNotNull(Integer id, Integer userId, Pageable pageable) {
        return apply.findAllByCompetitionIdAndScisUserIdAndScoreNotNull(id, userId, pageable);
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

    public Page<ScisApplyFrom> findByScoreBetween(Integer left, Integer right, Pageable pageable) {
        return apply.findByScoreBetween(left, right, pageable);
    }

    public Page<ScisApplyFrom> findByPrize(Integer grade, Pageable pageable) {
        switch (grade) {
            case 4:
                return apply.findByPrize("一等奖", pageable);
            case 3:
                return apply.findByPrize("二等奖", pageable);
            case 2:
                return apply.findByPrize("三等奖", pageable);
            default:
                return null;
        }
    }

    public Page<ScisApplyFrom> findByScoreBetweenAndScisUserId(Integer left, Integer right, Integer userId, Pageable pageable) {
        return apply.findByScoreBetweenAndScisUserId(left, right, userId, pageable);
    }

    public Page<ScisApplyFrom> findByScoreBetweenAndScisUserIdAndScoreNotNull(Integer left, Integer right, Integer userId, Pageable pageable) {
        return apply.findByScoreBetweenAndScisUserIdAndScoreNotNull(left, right, userId, pageable);
    }

    public Page<ScisApplyFrom> findByScisUserIdAndScoreNotNullAndPrize(Integer userId, Integer grade, Pageable pageable) {
        switch (grade) {
            case 4:
                return apply.findByScisUserIdAndScoreNotNullAndPrize(userId, "一等奖", pageable);
            case 3:
                return apply.findByScisUserIdAndScoreNotNullAndPrize(userId, "二等奖", pageable);
            case 2:
                return apply.findByScisUserIdAndScoreNotNullAndPrize(userId, "三等奖", pageable);
            default:
                return null;
        }
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
