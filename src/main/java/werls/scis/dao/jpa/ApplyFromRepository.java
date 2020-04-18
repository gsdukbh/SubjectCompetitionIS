package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import werls.scis.dao.pojo.ScisApplyFrom;

import java.util.List;
import java.util.Map;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 15:57
 */
public interface ApplyFromRepository extends JpaRepository<ScisApplyFrom, Integer> {

    /**
     * @param id
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Query(nativeQuery = true,
            value = "select d.team_id as teamId,\n" +
                    "       team_apply_time as applyTime,\n" +
                    "       team_captain as captain,\n" +
                    "       team_name as name,\n" +
                    "       team_number as number,\n" +
                    "       a.competition_id as competitionId,\n" +
                    "       a.competition_name as competitionName,\n" +
                    "       competition_start_time as startTime, " +
                    "       competition_place as place ," +
                    "       competition_type as type\n" +
                    "from Is_competition a,\n" +
                    "     Is_user b,\n" +
                    "     Is_team_user c,\n" +
                    "     Is_team_apply d\n" +
                    "where a.competition_id = d.competition_id\n" +
                    "  and d.team_id = c.team_id\n" +
                    "  and c.user_id = b.user_id\n" +
                    "  and b.user_id = ?1\n" +
                    "  and a.competition_name like concat('%', ?2, '%')\n" +
                    "limit ?3,?4")
    List<Map<String, Object>> findScisUserIdT(Integer id, String name, Integer page, Integer size);

    /**
     * s
     *
     * @param userId
     * @param competitionId
     */
    @Modifying
    @Query(nativeQuery = true, value = "update Is_apply_from\n" +
            "set works_id=?3\n" +
            "where user_id = ?1\n" +
            "  and competition_id = ?2")
    void update(Integer userId, Integer competitionId, Integer worksId);

    /**
     * @param id
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Query(nativeQuery = true,
            value = "SELECT b.apply_id             as applyId,\n" +
                    "       apply_time             as applyTime, " +
                    "        works_id  as  worksId, \n" +
                    "       c.competition_id       as competitionId,\n" +
                    "       competition_name       as name,\n" +
                    "       competition_start_time as startTime," +
                    "       competition_place as place ," +
                    "       competition_type as type \n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id=c.competition_id\n" +
                    "  and a.user_id = ?1\n" +
                    "and c.competition_name like concat('%', ?2, '%')" +
                    "limit ?3,?4")
    List<Map<String, Object>> findScisUserIdA(Integer id, String name, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "SELECT b.apply_id             as applyId,\n" +
                    "       apply_time             as applyTime,\n" +
                    "        works_id  as  worksId, \n" +
                    "       c.competition_id       as competitionId,\n" +
                    "       competition_name       as name,\n" +
                    "       competition_start_time as startTime," +
                    "       competition_place as place ," +
                    "       competition_type as type \n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id=c.competition_id\n" +
                    "  and a.user_id = ?1 " +
                    " and  c.competition_end_time > now()\n")
    List<Map<String, Object>> findScisUserIdA(Integer id);

    /**
     * 报名状态 分页 排序
     *
     * @param status   状态
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByStatus(String status, Pageable pageable);

    /**
     * 竞赛报名
     *
     * @param id       Integer
     * @param pageable Pageable
     * @return Page<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findAllByCompetitionId(Integer id, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select a.user_id as userId,\n" +
                    "       b.apply_time as applyTime\n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and c.competition_id=?1\n" +
                    "and  a.user_name like  concat('%',?2,'%') " +
                    "ORDER BY  b.apply_time DESC " +
                    "limit ?3,?4")
    List<Map<String, Object>> findCompetitionId(Integer id, String name, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select a.user_id as userId,\n" +
                    "       b.apply_time as applyTime ,\n " +
                    "       c.competition_id as competitionId ," +
                    "      competition_name as competitionName   " +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and c.competition_id=?1\n" +
                    "ORDER BY  b.apply_time DESC "
    )
    List<Map<String, Object>> findCompetitionId(Integer id);

    List<ScisApplyFrom> findAllByCompetitionId(Integer id);
}
