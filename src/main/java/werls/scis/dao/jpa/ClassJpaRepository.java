package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.dao.pojo.ScisUser;

import java.util.List;

/**
 * 班级 处理
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月24日 14:05
 */
public interface ClassJpaRepository extends JpaRepository<ScisClass,Integer> {

    /**
     * 使用班级名称查询，全称查询
     * @param name 班级名称
     * @return 班级
     */
    ScisClass findByName(String name);

    /**
     * 分页查询，可在pageable 加入 Sort
     * @param name 班级名称
     * @param pageable pageable
     * @return Page<ScisClass>
     */
    Page<ScisClass> findByName(String name, Pageable pageable);

    /**
     * 模糊查询，分页查询，可在pageable 加入 Sort
     * @param name 班级名称
     * @param pageable pageable
     * @return Page<ScisClass>
     */
    Page<ScisClass> findByNameLike(String name,Pageable pageable);


    /**
     * 使用年级查询,可在pageable 加入 Sort,分页查询
     * @param grade 年级
     * @param pageable Pageable
     * @return Page<ScisClass>
     */
    Page<ScisClass> findByGrade(String grade,Pageable pageable);

    /**
     * 模糊查询，可在pageable 加入 Sort,分页查询
     * @param grade 年级
     * @param pageable Pageable
     * @return Page<ScisClass>
     */
    Page<ScisClass> findByGradeLike(String grade,Pageable pageable);
}
