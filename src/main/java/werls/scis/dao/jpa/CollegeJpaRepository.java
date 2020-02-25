package werls.scis.dao.jpa;

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

}
