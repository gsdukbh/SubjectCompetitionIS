package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import werls.scis.dao.pojo.ScisTeamApply;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 23:29
 */
public interface TeamApplyRepository extends JpaRepository<ScisTeamApply, Integer> {
    /**
     * 通过名称模糊查询，分页 排序
     *
     * @param nameLike String 团队名称
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByNameLike(String nameLike, Pageable pageable);

    /**
     * 相关竞赛团队
     *
     * @param id       Integer CompetitionId
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findAllByCompetitionId(Integer id, Pageable pageable);

    /**
     * 相关竞赛团队名称
     *
     * @param name     名称
     * @param id       CompetitionId
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByNameContainingAndCompetitionId(String name, Integer id, Pageable pageable);

    /**
     * 参赛状态 分页 排序
     *
     * @param status   String 状态
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByStatus(String status, Pageable pageable);

    /**
     * 团队人数查询
     *
     * @param number Integer
     * @return age<ScisTeamApply>
     */
    Page<ScisTeamApply> findByNumber(Integer number, Pageable pageable);
}
