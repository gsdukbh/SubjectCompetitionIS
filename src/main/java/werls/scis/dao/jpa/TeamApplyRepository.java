package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import werls.scis.dao.pojo.ScisTeamApply;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 23:29
 */
public interface TeamApplyRepository extends JpaRepository<ScisTeamApply,Integer> {
    /**
     * 通过名称模糊查询，分页 排序
     * @param nameLike String 团队名称
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByNameLike(String nameLike ,Pageable pageable);

    /**
     * 参赛状态 分页 排序
     * @param status String 状态
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByStatus(String status,Pageable pageable);

    /**
     * 按照团队分数查询 确定分数 排序
     * @param score  Integer 分数
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByScore(Integer score,Pageable pageable);

    /**
     * > score 团队
     * @param scoreAfter Integer
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByScoreAfter(Integer scoreAfter,Pageable pageable);

    /**
     *  < score 团队分数
     * @param scoreBefore Integer
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByScoreBefore(Integer scoreBefore,Pageable pageable);

    /**
     * start <= score <= end
     * @param start Integer
     * @param end Integer
     * @param pageable Pageable
     * @return  Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByScoreBetween(Integer start,Integer end, Pageable pageable);

    /**
     * 团队人数查询
     * @param number Integer
     * @return age<ScisTeamApply>
     */
    Page<ScisTeamApply> findByNumber(Integer number, Pageable pageable);
}
