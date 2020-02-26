package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisProblem;

import java.sql.Date;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 22:58
 */
public interface ProblemRepository extends JpaRepository<ScisProblem,Integer> {
    /**
     * 指定时间查询 分页 排序
     * @param time  java.sql.Date;
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
    Page<ScisProblem>findByTime(Date time , Pageable pageable);

    /**
     * 问题类型 查询 分页 排序
     * @param type String 问题类型
     * @return Page<ScisProblem>
     */
    Page<ScisProblem> findByType(String type , Pageable pageable);

    /**
     * 按照标题模糊查询 分页 排序
     * @param title String 标题
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
    Page<ScisProblem>findByTitleLike(String title, Pageable pageable);

}
