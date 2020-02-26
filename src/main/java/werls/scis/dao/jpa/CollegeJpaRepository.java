package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisCollege;

import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月25日 9:30
 */
public interface CollegeJpaRepository extends JpaRepository<ScisCollege,Integer> {
    /**
     * 通过学院名称查询
     * @param collegeName 学院名称
     * @return 学院
     */
    ScisCollege findByCollegeName(String collegeName);


    /**
     * 通过学院名称查询 模糊 分页 排序
     * @param collegeName 学院名称
     * @param pageable Pageable
     * @return Page<ScisCollege>
     */
    Page<ScisCollege> findByCollegeNameLike(String collegeName, Pageable pageable);

}
