package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.dao.pojo.ScisWorks;

import javax.persistence.criteria.CriteriaBuilder;

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
     * 模糊查询 作品名
     *
     * @param nameLike String
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByNameContaining(String nameLike, Pageable pageable);

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
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByScore(Integer score,Pageable pageable);

    /**
     *  > score
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks>findByScoreAfter(Integer score, Pageable pageable);

    /**
     * < score
     * @param score Integer
     * @param pageable Pageable
     * @return    Page<ScisWorks>
     */
    Page<ScisWorks> findByScoreBefore(Integer score , Pageable pageable);

    /**
     *  start <= score <=end
     * @param start Integer
     * @param end Integer
     * @param pageable  Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByScoreBetween(Integer start ,Integer end , Pageable pageable);

    /**
     * author and ? > score
     * @param author String
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorAndScoreAfter(String author,Integer score,Pageable pageable);

    /**
     * author and ? < score
     * @param author String
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorAndScoreBefore(String author,Integer score,Pageable pageable);

    /**
     * author and start <= score <= end
     * @param author  String
     * @param start Integer
     * @param end Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    Page<ScisWorks> findByAuthorAndScoreBetween(String author,Integer start,Integer end,Pageable pageable);

    /**
     * 查询竞赛作品 需要 ScisCompetition ID
     * @param competition ScisCompetition
     * @param pageable Pageable
     * @return  Page<ScisWorks>
     */
    Page<ScisWorks> findByCompetition(ScisCompetition competition,Pageable pageable);


}
