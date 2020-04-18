package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.dao.pojo.ScisWorks;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月27日 11:09
 */
public interface WorksRepository extends JpaRepository<ScisWorks, Integer> {

    /**
     * @param id
     * @param name
     * @return
     */
    @Query(nativeQuery = true,
            value = "select d.works_id,\n" +
                    "       d.works_author,\n" +
                    "       d.works_bucketName,\n" +
                    "       d.works_description,\n" +
                    "       d.works_img,\n" +
                    "       d.works_name,\n" +
                    "       d.works_objectName,\n" +
                    "       d.works_score,\n" +
                    "       d.works_up_time\n" +
                    "from Is_apply_from a,\n" +
                    "     Is_user b,\n" +
                    "     Is_works d\n" +
                    "where a.works_id = d.works_id\n" +
                    "  and b.user_id = a.user_id\n" +
                    "  and a.user_id = ?1\n" +
                    "  and d.works_name like concat('%',?2,'%') " +
                    "ORDER BY d.works_score DESC\n" +
                    "limit ?3,?4")
    List<Map<String, Object>> findUserIdName(Integer id, String name, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select d.works_id          as id,\n" +
                    "       d.works_author      as author,\n" +
                    "       d.works_bucketName  as bucketName,\n" +
                    "       d.works_description as description,\n" +
                    "       d.works_img         as img,\n" +
                    "       d.works_name        as name,\n" +
                    "       d.works_objectName  as objectName,\n" +
                    "       d.works_score       as score,\n" +
                    "       d.works_up_time     as upTime,\n" +
                    "       a.competition_id    as competitionId,\n" +
                    "    c.competition_name as competitionName\n" +
                    "from Is_apply_from a,\n" +
                    "     Is_user b,\n" +
                    "     Is_competition c,\n" +
                    "     Is_works d\n" +
                    "where (a.works_id = d.works_id\n" +
                    "  and b.user_id = a.user_id\n" +
                    "  and a.user_id = ?1\n" +
                    "  and a.competition_id = c.competition_id\n" +
                    "  and d.works_name like concat('%', ?2, '%'))\n" +
                    "  or (  a.works_id = d.works_id\n" +
                    "    and a.competition_id = c.competition_id\n" +
                    "    and b.user_id = a.user_id\n" +
                    "    and a.user_id = ?1 \n" +
                    "    and c.competition_id = ?3 )\n" +
                    "ORDER BY d.works_score DESC\n" +
                    "limit ?4,?5")
    List<Map<String, Object>> findUserIdWorksNameOrCompetition(Integer id, String name, Integer competitionId, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select COUNT(*) as totalElements\n" +
                    "from Is_apply_from a,\n" +
                    "     Is_user b,\n" +
                    "     Is_competition c,\n" +
                    "     Is_works d\n" +
                    "where (a.works_id = d.works_id\n" +
                    "    and b.user_id = a.user_id\n" +
                    "    and a.user_id = ?1\n" +
                    "    and a.competition_id = c.competition_id\n" +
                    "    and d.works_name like concat('%', ?2, '%'))\n" +
                    "   or (  a.works_id = d.works_id\n" +
                    "    and a.competition_id = c.competition_id\n" +
                    "    and b.user_id = a.user_id\n" +
                    "    and a.user_id = ?1\n" +
                    "    and c.competition_id = ?3 )")
    Map<String, Object> findUserIdWorksNameOrCompetition(Integer id, String name, Integer competitionId);

    @Query(nativeQuery = true,
            value = "select a.works_id as worksId\n" +
                    "from Is_apply_from a\n" +
                    "where a.competition_id=?1 and a.user_id=?2")
    Map<String, Object> find(Integer userId, Integer competitionId);

    /**
     * 模糊查询 作品名
     *
     * @param nameLike String
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByNameContaining(String nameLike, Pageable pageable);

    /**
     * sss
     *
     * @param name
     * @param competitionId
     * @param pageable
     * @return
     */
    Page<ScisWorks> findByNameContainingAndCompetitionId(String name, Integer competitionId, Pageable pageable);

    /**
     * s
     *
     * @param worksName
     * @param author
     * @param competitionId
     * @param pageable
     * @return
     */
    Page<ScisWorks> findByNameContainingAndAuthorContainingAndCompetitionId(String worksName, String author, Integer competitionId, Pageable pageable);

    /**
     * wed
     *
     * @param worksName
     * @param author
     * @param pageable
     * @return
     */
    Page<ScisWorks> findByNameContainingAndAuthorContaining(String worksName, String author, Pageable pageable);

    /**
     * s
     *
     * @param author
     * @param competitionId
     * @param pageable
     * @return
     */
    Page<ScisWorks> findByAuthorContainingAndCompetitionId(String author, Integer competitionId, Pageable pageable);

    Page<ScisWorks> findByCompetitionId(Integer competitionId, Pageable pageable);

    List<ScisWorks> findByCompetitionId(Integer competitionId);

    /**
     * author By Like sort page
     *
     * @param authorLike String
     * @param pageable   Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorContaining(String authorLike, Pageable pageable);


    /**
     * 作品分数
     *
     * @param score    Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByScore(Integer score, Pageable pageable);

    /**
     * > score
     *
     * @param score    Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByScoreAfter(Integer score, Pageable pageable);

    /**
     * < score
     *
     * @param score    Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByScoreBefore(Integer score, Pageable pageable);

    /**
     * start <= score <=end
     *
     * @param start    Integer
     * @param end      Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByScoreBetween(Integer start, Integer end, Pageable pageable);

    /**
     * author and ? > score
     *
     * @param author   String
     * @param score    Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorAndScoreAfter(String author, Integer score, Pageable pageable);

    /**
     * author and ? < score
     *
     * @param author   String
     * @param score    Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorAndScoreBefore(String author, Integer score, Pageable pageable);

    /**
     * author and start <= score <= end
     *
     * @param author   String
     * @param start    Integer
     * @param end      Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorAndScoreBetween(String author, Integer start, Integer end, Pageable pageable);

    /**
     * 查询竞赛作品 需要 ScisCompetition ID
     *
     * @param competition ScisCompetition
     * @param pageable    Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByCompetition(ScisCompetition competition, Pageable pageable);


}
