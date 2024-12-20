package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import werls.scis.dao.pojo.ScisProblem;

import java.util.Date;


/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 22:58
 */
public interface ProblemRepository extends JpaRepository<ScisProblem, Integer> {
    /**
     * 指定时间查询 分页 排序
     *
     * @param time     java.sql.Date;
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
    Page<ScisProblem> findByTime(Date time, Pageable pageable);

    /**
     * 问题类型 查询 分页 排序
     *
     * @param type String 问题类型
     * @return Page<ScisProblem>
     */
    Page<ScisProblem> findByType(String type, Pageable pageable);

    /**
     * 按照标题模糊查询 分页 排序
     *
     * @param title    String 标题
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
    Page<ScisProblem> findByTitleLike(String title, Pageable pageable);

    /**
     * 按照竞赛id进行查询
     *
     * @param id       id
     * @param pageable pageable
     * @return Page<ScisProblem>
     */
    @Query(nativeQuery = true, value =
            "select * " +
                    "from Is_problem " +
                    "where competition_id=?1"
    )
    Page<ScisProblem> myFindCompetitionId(Integer id, Pageable pageable);

    /**
     * 问题是否回复
     * @param id  Integer
     * @param isReply Boolean
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
    @Query(nativeQuery = true,
            value = "select *\n" +
                    "from Is_problem\n" +
                    "where problem_isReply = ?1 and competition_id=?2")
    Page<ScisProblem> findByMyReply(Integer isReply,Integer id,Pageable pageable);
}
