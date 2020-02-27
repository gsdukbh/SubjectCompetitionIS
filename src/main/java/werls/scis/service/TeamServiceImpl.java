package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.TeamApplyRepository;
import werls.scis.dao.pojo.ScisTeamApply;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl {
    @Autowired
    private TeamApplyRepository teamApplyRepository;

    /**
     * 通过名称模糊查询，分页 排序
     * @param nameLike String 团队名称
     * @return Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByNameLike(String nameLike , Pageable pageable){
        return teamApplyRepository.findByNameLike(nameLike, pageable);
    }

    /**
     * 参赛状态 分页 排序
     * @param status String 状态
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByStatus(String status,Pageable pageable){
        return teamApplyRepository.findByStatus(status, pageable);
    }
    /**
     * 按照团队分数查询 确定分数 排序
     * @param score  Integer 分数
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    public  Page<ScisTeamApply> findByScore(Integer score,Pageable pageable){
        return teamApplyRepository.findByScore(score, pageable);
    }
    /**
     * > score 团队
     * @param scoreAfter Integer
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByScoreAfter(Integer scoreAfter,Pageable pageable){
        return teamApplyRepository.findByScoreAfter(scoreAfter, pageable);
    }
    /**
     *  < score 团队分数
     * @param scoreBefore Integer
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByScoreBefore(Integer scoreBefore,Pageable pageable){
        return  teamApplyRepository.findByScoreBefore(scoreBefore, pageable);
    }
    /**
     * start <= score <= end
     * @param start Integer
     * @param end Integer
     * @param pageable Pageable
     * @return  Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByScoreBetween(Integer start,Integer end, Pageable pageable){
        return teamApplyRepository.findByScoreBetween(start, end, pageable);
    }
    /**
     * 团队人数查询
     * @param number Integer
     * @return age<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByNumber(Integer number, Pageable pageable){
        return  teamApplyRepository.findByNumber(number, pageable);
    }

}
