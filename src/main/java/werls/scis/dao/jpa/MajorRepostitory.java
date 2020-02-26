package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisMajor;

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
    Page<ScisMajor> findByMajorLevel(String majorLevel, Pageable pageable);

    /***
     * 按照专业名字查询
     * @param majorName 专业名字
     * @return ScisMajor
     */
    ScisMajor findByMajorName(String majorName);

}
