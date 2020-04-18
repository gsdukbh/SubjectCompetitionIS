package werls.scis.dao.jpa;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import werls.scis.dao.pojo.ScisCompetition;

import java.util.Date;
import java.util.List;

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
     * my join
     *
     * @param id              Integer
     * @param competitionName ScisCompetition name
     * @return List ScisCompetition
     */
    @Query(nativeQuery = true,
            value = "select d.competition_id,\n" +
                    "       d.competition_apply_time,\n" +
                    "       d.competition_author,\n" +
                    "       d.competition_bucketName,\n" +
                    "       d.competition_content,\n" +
                    "       d.competition_end_time,\n" +
                    "       d.competition_img,\n" +
                    "       d.competition_level,\n" +
                    "       d.competition_name,\n" +
                    "       d.competition_num_limit,\n" +
                    "       d.competition_objectName,\n" +
                    "       d.competition_organizer,\n" +
                    "       d.competition_place,\n" +
                    "       d.competition_principal,\n" +
                    "       d.competition_start_time,\n" +
                    "       d.competition_status,\n" +
                    "       d.competition_isTeam,\n" +
                    "       d.competition_type,\n" +
                    "       d.user_id\n" +
                    "from Is_user a,\n" +
                    "     Is_team_user b,\n" +
                    "     Is_team_apply c,\n" +
                    "     Is_competition d\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.team_id = c.team_id\n" +
                    "  and c.competition_id = d.competition_id\n" +
                    "  and a.user_id = ?1\n" +
                    "  and d.competition_name like concat('%',?2,'%')" +
                    " limit ?3,?4")
    List<ScisCompetition> findByApplyAndUserTeam(Integer id, String competitionName, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select c.competition_id,\n" +
                    "       c.competition_apply_time,\n" +
                    "       c.competition_author,\n" +
                    "       c.competition_bucketName,\n" +
                    "       c.competition_content,\n" +
                    "       c.competition_end_time,\n" +
                    "       c.competition_img,\n" +
                    "       c.competition_level,\n" +
                    "       c.competition_name,\n" +
                    "       c.competition_num_limit,\n" +
                    "       c.competition_objectName,\n" +
                    "       c.competition_organizer,\n" +
                    "       c.competition_place,\n" +
                    "       c.competition_principal,\n" +
                    "       c.competition_start_time,\n" +
                    "       c.competition_status,\n" +
                    "       c.competition_isTeam,\n" +
                    "       c.competition_type,\n" +
                    "       c.user_id\n" +
                    "from Is_user a,\n" +
                    "     Is_apply_from b,\n" +
                    "     Is_competition c\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and c.competition_id = b.competition_id\n" +
                    "  and a.user_id = ?1\n" +
                    "  and c.competition_name like concat('%',?2,'%') " +
                    "limit ?3,?4")
    List<ScisCompetition> findApplyUser(Integer id, String competitionName, Integer page, Integer size);

    /**
     * 模糊查询竞赛名称
     *
     * @param name     String
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContaining(String name, Pageable pageable);

    Page<ScisCompetition> findByNameContainingAndStatusIsNot(String name, String status, Pageable pageable);

    /**
     * 竞赛状态
     *
     * @param status   String 状态
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByStatus(String status, Pageable pageable);

    /**
     * 按照发布者模糊查询
     *
     * @param authorLike String 发布者
     * @param pageable   Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByAuthorLike(String authorLike, Pageable pageable);

    /**
     * 竞赛级别
     *
     * @param level    String 竞赛级别
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByLevel(String level, Pageable pageable);

    Page<ScisCompetition> findByLevelAndStatusIsNot(String level, String status, Pageable pageable);

    Page<ScisCompetition> findAllByStatusIsNot(String status, Pageable pageable);

    List<ScisCompetition> findAllByStatusIsNot(String status);

    /**
     * 举办单位
     *
     * @param organizer String 举办单位
     * @param pageable  Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByOrganizer(String organizer, Pageable pageable);

    Page<ScisCompetition> findByOrganizerAndStatusNot(String organizer, String status, Pageable pageable);

    Page<ScisCompetition> findByOrganizerAndStatusIsNot(String organizer, String status, Pageable pageable);

    /**
     * 通过名称和举办单位查询
     *
     * @param name      String 赛事名称
     * @param organizer String 举办单位
     * @param pageable  Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingAndOrganizer(String name, String organizer, Pageable pageable);

    Page<ScisCompetition> findByNameContainingAndOrganizerAndStatusIsNot(String name, String organizer, String status, Pageable pageable);

    /**
     * ..
     *
     * @param name      String
     * @param organizer String
     * @param level     String
     * @param pageable  Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingOrOrganizerOrLevel(String name, String organizer, String level, Pageable pageable);

    /**
     * ..
     *
     * @param name      String
     * @param level     String
     * @param organizer String
     * @param pageable  Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingAndLevelAndOrganizer(String name, String level, String organizer, Pageable pageable);

    /**
     * 已经公开的
     *
     * @param name      String
     * @param level     String
     * @param organizer String
     * @param status    String
     * @param pageable  Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingAndLevelAndOrganizerAndStatusNot(String name, String level, String organizer, String status,
                                                                               Pageable pageable);

    /**
     * 11
     *
     * @param level    String
     * @param name     String
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingAndLevel(String name, String level, Pageable pageable);

    /**
     * 11
     *
     * @param name     String
     * @param level    String
     * @param status   String
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingAndLevelAndStatusIsNot(String name, String level, String status, Pageable pageable);

    /**
     * 11
     *
     * @param organizer String
     * @param name      String
     * @param pageable  Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByOrganizerAndLevel(String organizer, String name, Pageable pageable);

    Page<ScisCompetition> findByOrganizerAndLevelAndStatusIsNot(String organizer, String name, String status, Pageable pageable);

    /**
     * 查找标题
     *
     * @return List<String>
     */
    @Query(nativeQuery = true,
            value = "select distinct competition_name from Is_competition;")
    List<String> findName();

    /**
     * 用户&&name
     *
     * @param name     String
     * @param userId   Integer userId
     * @param pageable Pageable
     * @return Page<ScisCompetition>
     */
    Page<ScisCompetition> findByNameContainingAndUserId(String name, Integer userId, Pageable pageable);

    /**
     * upStatus
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update Is_competition set competition_status='进行中' " +
                    "where competition_start_time <=NOW()")
    void upStatus();

    /**
     * upStatus
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update Is_competition set competition_status='已结束'\n" +
                    "where competition_end_time <=NOW()")
    void upStatusA();

    /**
     * 竞赛开始时间在date之后
     *
     * @param date Date
     * @return List<ScisCompetition>
     */
    List<ScisCompetition> findByStartTimeAfter(Date date);

    /**
     * 竞赛开始时间在date之前
     *
     * @param date Date
     * @return List<ScisCompetition>
     */
    List<ScisCompetition> findByStartTimeBefore(Date date);
}
