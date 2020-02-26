package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import werls.scis.dao.pojo.ScisApplyFrom;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 15:57
 */
public interface ApplyFromRepository extends JpaRepository<ScisApplyFrom,Integer> {

    /**
     * 报名状态 分页 排序
     * @param status 状态
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByStatus(String status, Pageable pageable);
    /**
     * 分数查询 分页 排序
     * @param score 分数 Integer
     * @param pageable Pageable
     * @return Page<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByScore(Integer score, Pageable pageable);

    /**
     * > score 的集合 分页 排序
     * @param scoreAfter 分数 Intege
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByScoreAfter(Integer scoreAfter,Pageable pageable);

    /**
     * < score 的集合 分页 排序
     * @param scoreBefore 分数 Integer
     * @param pageable Pageable
     * @return Page<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByScoreBefore(Integer scoreBefore,Pageable pageable);

    /**
     * 区间查询 score 的集合 分页 排序
     * @param start 开始值 Integer
     * @param end 结束值 Integer
     * @param pageable Pageable
     * @return Page<ScisApplyFrom>
     */
    Page<ScisApplyFrom> findByScoreBetween(Integer start,Integer end ,Pageable pageable);

}
