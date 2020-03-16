package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import werls.scis.dao.pojo.ScisCompetition;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 19:23
 */
public interface CompetitionRepository extends JpaRepository<ScisCompetition, Integer> {
    /**
     * 模糊查询竞赛名称
     * @param name String
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameLike(String name, Pageable pageable);

    /**
     * 竞赛状态
     * @param status String 状态
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByStatus(String status, Pageable pageable);

    /**
     * 按照发布者模糊查询
     * @param authorLike String 发布者
     * @param pageable Pageable
     * @return  Page<ScisCompetition>
     */
    Page<ScisCompetition> findByAuthorLike(String authorLike,Pageable pageable);

    /**
     * 竞赛级别
     * @param level String 竞赛级别
     * @param pageable Pageable
     * @return  Page<ScisCompetition>
     */
    Page<ScisCompetition> findByLevel(String level, Pageable pageable);

    /**
     * 举办单位
     * @param organizer String 举办单位
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByOrganizer(String organizer, Pageable pageable);

    /**
     * 固定url
     * @param url 赛事固定url
     * @return ScisCompetition
     */
    ScisCompetition findByUrl(String url);

    /**
     * 通过名称和举办单位查询
     * @param name String 赛事名称
     * @param organizer String 举办单位
     * @param pageable  Pageable
     * @return  Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameLikeAndOrganizer(String name,String organizer,Pageable pageable);
}
