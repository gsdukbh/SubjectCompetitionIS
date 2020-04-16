package werls.scis.dao.jpa;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import werls.scis.dao.pojo.ScisTeamApply;

import java.util.List;
import java.util.Map;

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
     * sd
     *
     * @param userId          Integer
     * @param competitionName String
     * @param teamName        String
     * @param isCaptain       Boolean
     * @param page            Integer
     * @param size            Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.team_id              as teamId,\n" +
                    "       isCaptain,\n" +
                    "       isApply,\n" +
                    "       isRead, \n " +
                    "   applyTeam ," +
                    "       b.team_apply_time      as applyTime,\n" +
                    "       team_captain           as captain,\n" +
                    "       team_name              as teamName,\n" +
                    "       c.competition_id       as competitionId,\n" +
                    "       competition_type       as type,\n" +
                    "       competition_start_time as startTime,\n" +
                    "       competition_name       as competitionName,\n" +
                    "       competition_place      as place,\n" +
                    "       competition_end_time   as endTime, " +
                    "       d.user_id as userId \n" +
                    "from Is_team_user a,\n" +
                    "     Is_team_apply b,\n" +
                    "     Is_competition c,\n" +
                    "     Is_user d \n" +
                    "where d.user_id = a.user_id\n" +
                    "  and b.team_id = a.team_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and d.user_id = ?1\n" +
                    "  and c.competition_name like concat('%', ?2, '%')\n" +
                    "  and b.team_name like concat('%', ?3, '%') \n" +
                    "  and a.isCaptain=?4 " +
                    "limit ?5,?6")
    List<Map<String, Object>> findMyTeamIsCaptain(Integer userId, String competitionName, String teamName, Boolean isCaptain, Integer page, Integer size);


    /**
     * 删除
     *
     * @param teamId Integer
     * @param userId Integer
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "delete from Is_team_user\n" +
                    "where team_id=?1\n" +
                    "and user_id=?2")
    void deleteTeamUser(Integer teamId, Integer userId);


    /**
     * s
     *
     * @param teamId Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.user_id  as userId,\n" +
                    "       user_login as login,\n" +
                    "       user_name  as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone as phone, " +
                    "       user_avatar  as avatar ,\n" +
                    "       b.isCaptain ,\n" +
                    "       isApply,\n" +
                    "       isRead ,\n " +
                    "       applyTeam as applyTeamTime ," +
                    "       b.team_id as teamId " +
                    "from Is_user a,\n" +
                    "     Is_team_user b,\n" +
                    "     Is_team_apply c,\n" +
                    "     Is_competition d\n" +
                    "where a.user_id = b.user_id\n" +
                    "  and b.team_id = c.team_id\n" +
                    "  and c.competition_id = d.competition_id\n" +
                    "  and b.team_id = ?1 " +
                    "and b.isApply=?2")
    List<Map<String, Object>> findTeamMember(Integer teamId, Boolean isApply);

    /**
     * ss
     *
     * @param userId          Integer
     * @param competitionName String
     * @param teamName        String
     * @param page            Integer
     * @param size            Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.team_id              as teamId,\n" +
                    "       isCaptain,\n" +
                    "       isApply,\n" +
                    "       isRead,\n" +
                    "       b.team_apply_time      as applyTime,\n" +
                    "       team_captain           as captain,\n" +
                    "       team_name              as teamName,\n" +
                    "       c.competition_id       as competitionId,\n" +
                    "       competition_type       as type,\n" +
                    "       competition_start_time as startTime,\n" +
                    "       competition_name       as competitionName,\n" +
                    "       competition_place      as place,\n" +
                    "       competition_end_time   as endTime\n" +
                    "from Is_team_user a,\n" +
                    "     Is_team_apply b,\n" +
                    "     Is_competition c,\n" +
                    "     Is_user d\n" +
                    "where d.user_id = a.user_id\n" +
                    "  and b.team_id = a.team_id\n" +
                    "  and b.competition_id = c.competition_id\n" +
                    "  and d.user_id =?1 \n" +
                    "  and c.competition_name like concat('%', ?2, '%')\n" +
                    "  and b.team_name like concat('%', ?3, '%')\n" +
                    "limit ?4,?5")
    List<Map<String, Object>> findMyTeam(Integer userId, String competitionName, String teamName, Integer page, Integer size);

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
