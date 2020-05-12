package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import werls.scis.dao.pojo.ScisMajor;

import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 20:34
 */
public interface MajorRepostitory extends JpaRepository<ScisMajor,Integer> {
    /**
     *
     * @param majorLevel String 培养层次
     * @return Page<ScisMajor>
     */
    Page<ScisMajor> findByLevel(String majorLevel, Pageable pageable);

    /***
     * 按照专业名字查询
     * @param majorName 专业名字
     * @return ScisMajor
     */
    Optional<ScisMajor> findByName(String majorName);

    /**
     * name find
     *
     * @param majorName name
     * @param pageable  page
     * @return Page<ScisMajor>
     */
    Page<ScisMajor> findByNameContaining(String majorName, Pageable pageable);

    @Modifying
    @Query(nativeQuery = true,
            value = "delete from Is_major where major_id =?1")
    void myDeleteById(Integer id);
}
