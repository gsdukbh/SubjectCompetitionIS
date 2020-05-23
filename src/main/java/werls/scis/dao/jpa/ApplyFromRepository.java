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


    Page<ScisApplyFrom> findByScisUserLoginContaining(String login, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserNameContaining(String name, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserNameContainingOrScisUserLoginContaining(String name, String login, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserNameContainingOrScisUserLoginContainingAndScoreBetween(String name, String login, Integer left, Integer right, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserId(Integer id, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserIdAndScoreNotNull(Integer id, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserIdAndCompetitionIdAndScoreBetween(Integer userId, Integer competitionId, Integer left, Integer right, Pageable pageable);

    Page<ScisApplyFrom> findByScisUserIdAndCompetitionIdAndScoreBetweenAndScoreNotNull(Integer userId, Integer competitionId, Integer left, Integer right, Pageable pageable);

    /**
     * s
     *
     * @param competitionId
     * @param left
     * @param right
     * @return
     */
    @Query(nativeQuery = true, value = "select count(*)\n" +
            "from Is_apply_from a,\n" +
            "     Is_user b,\n" +
            "     Is_college c,\n" +
            "     Is_major d,\n" +
            "     Is_class e\n" +
            "where a.user_id = b.user_id\n" +
            "  and c.college_id = d.college_id\n" +
            "  and e.major_id = d.major_id\n" +
            "  and b.class_id = e.class_id\n" +
            "  and a.competition_id = ?1\n" +
            "  and a.apply_score between ?2 and ?3")
    int gradeDistribution(Integer competitionId, Integer left, Integer right);

    /**
     * 实时查询排名
     *
     * @param applyId
     * @return
     */
    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "\t( SELECT count( b.apply_score ) \n" +
                    "\t\tFROM Is_apply_from b\n" +
                    "\t\tWHERE b.apply_score >= a.apply_score ) AS Rank \n" +
                    "FROM\n" +
                    "\tIs_apply_from a \n" +
                    "WHERE\n" +
                    "\ta.apply_id = ?1\n" +
                    "ORDER BY\n" +
                    "\ta.apply_score DESC;")
    Integer getGradeRank(Integer applyId);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "from Is_apply_from a,\n" +
            "     Is_user b,\n" +
            "     Is_college c,\n" +
            "     Is_major d,\n" +
            "     Is_class e\n" +
            "where a.user_id = b.user_id\n" +
            "  and c.college_id = d.college_id\n" +
            "  and e.major_id = d.major_id\n" +
            "  and b.class_id = e.class_id\n" +
            "  and a.competition_id = ?1\n" +
            "  and a.apply_score between ?2 and ?3 " +
            "and  c.college_name =?4")
    int gradeDistribution(Integer competitionId, Integer left, Integer right, String college);

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

    @Query(nativeQuery = true,
            value = "select *\n" +
                    "from Is_apply_from  a\n" +
                    "where a.user_id=?1\n" +
                    "and a.competition_id=?2")
    ScisApplyFrom findByUserIdCompetitionId(Integer userId, Integer competitionId);

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
                    " c.competition_apply_stop as applyStop," +
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
            value = "SELECT count(*) as totalElements\n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and a.user_id = ?1\n" +
                    "  and c.competition_name like concat('%',?2,'%')")
    Map<String, Object> findScisUserIdA(Integer id, String name);

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

    @Query(nativeQuery = true,
            value = "SELECT count(*) as totalElements\n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and a.user_id = ?1\n" +
                    " and  c.competition_end_time > now() \n")
    Map<String, Object> findScisUserIdB(Integer id);

    @Query(nativeQuery = true,
            value = "select a.apply_score      as score,\n" +
                    "       apply_rank         as gradesRanking,\n" +
                    "       b.user_login       as login,\n" +
                    "       b.user_name        as name,\n" +
                    "       a.user_id          as userId,\n" +
                    "       d.competition_name as competitionName,\n" +
                    "       d.competition_id   as competitionId,\n" +
                    "       e.class_name as className,\n" +
                    "       f.major_name as majorName,\n" +
                    "       g.college_name as collegeName\n" +
                    "from Is_apply_from a,\n" +
                    "     Is_user b,\n" +
                    "     Is_works c,\n" +
                    "     Is_competition d,\n" +
                    "     Is_class e,\n" +
                    "     Is_major f,\n" +
                    "     Is_college g\n" +
                    "where a.competition_id = d.competition_id\n" +
                    "  and a.works_id = c.works_id\n" +
                    "  and a.user_id = b.user_id\n" +
                    "  and b.class_id = e.class_id\n" +
                    "  and e.major_id = f.major_id\n" +
                    "  and f.college_id = g.college_id\n" +
                    "  and d.competition_id = ?1\n" +
                    "  and b.user_name like concat('%', ?2, '%')\n" +
                    "ORDER BY a.apply_score desc\n" +
                    "limit ?3,?4")
    List<Map<String, Object>> findGrades(Integer competitionId, String userName, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select count(*) as totalElements\n" +
                    "from Is_apply_from a,\n" +
                    "     Is_user b,\n" +
                    "     Is_works c,\n" +
                    "     Is_competition d\n" +
                    "where a.competition_id = d.competition_id\n" +
                    "  and a.works_id = c.works_id\n" +
                    "  and a.user_id = b.user_id\n" +
                    "  and b.user_name like concat('%', ?1, '%')\n" +
                    "  and d.competition_id = ?2\n")
    Map<String, Object> findGrades(String userName, Integer competitionId);

    /**
     * 报名状态 分页 排序
     *
     * @param status   状态
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByStatus(String status, Pageable pageable);

    /**
     * @param left
     * @param right
     * @return
     */
    Page<ScisApplyFrom> findByScoreBetween(Integer left, Integer right, Pageable pageable);

    Page<ScisApplyFrom> findByScoreBetweenAndScisUserId(Integer left, Integer right, Integer userId, Pageable pageable);

    Page<ScisApplyFrom> findByScoreBetweenAndScisUserIdAndScoreNotNull(Integer left, Integer right, Integer userId, Pageable pageable);

    /**
     * 竞赛报名
     *
     * @param id       Integer
     * @param pageable Pageable
     * @return Page<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findAllByCompetitionId(Integer id, Pageable pageable);

    Page<ScisApplyFrom> findAllByCompetitionIdAndScisUserId(Integer id, Integer userId, Pageable pageable);

    Page<ScisApplyFrom> findAllByCompetitionIdAndScisUserIdAndScoreNotNull(Integer id, Integer userId, Pageable pageable);

    /**
     * 用户id
     *
     * @param id ApplyId Integer
     * @return Map<String, Object> userId ,integer 用户id
     */
    @Query(nativeQuery = true,
            value = "select a.user_id as userId\n" +
                    "from Is_apply_from  a\n" +
                    "where\n" +
                    "  a.apply_id=?1")
    Map<String, Object> findUserIdByApplyId(Integer id);

    @Query(nativeQuery = true,
            value = "select a.user_id as userId,\n" +
                    "       b.apply_time as applyTime\n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and c.competition_id=?1\n" +
                    "  and  ( a.user_name like  concat('%',?2,'%') " +
                    " or a.user_id=?2)" +
                    "ORDER BY  b.apply_time DESC " +
                    "limit ?3,?4")
    List<Map<String, Object>> findCompetitionId(Integer id, String name, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select a.user_id as userId,\n" +
                    "       b.apply_time as applyTime ," +
                    " b.apply_score as score,\n" +
                    " b.apply_rank as grades, " +
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
