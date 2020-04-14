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
public interface ApplyFromRepository extends JpaRepository<ScisApplyFrom, Integer> {


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


}
