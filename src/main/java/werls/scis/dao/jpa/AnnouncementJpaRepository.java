package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisClass;

import java.sql.Date;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月25日 10:14
 */
public interface AnnouncementJpaRepository extends JpaRepository<ScisAnnouncement,Long> {
    /**
     * java.sql.Date 按照时间查询
     * @param timeAfter 公告时间在此之后
     * @return 公告
     */
    List<ScisAnnouncement> findByTimeIsAfter(Date timeAfter);

    /**
     * 公告时间在此之后
     * @param timeAfter 时间 java.sql.Date
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByTimeIsAfter(Date timeAfter, Pageable pageable);

    /**
     * 公告当天时间
     * @param date java.sql.Date
     * @param pageable Pageable
     * @return  Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByTime(Date date, Pageable pageable);

    /**
     * 公告当天时间
     * @param date java.sql.Date
     * @return  List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByTime(Date date);

    /**
     * 公告时间之前
     * @param timeBefore java.sql.Date
     * @return List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByTimeBefore(Date timeBefore);

    /**
     * 公告时间之前
     * @param timeBefore java.sql.Date
     * @param pageable Pageable
     * @return  List<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByTimeBefore(Date timeBefore, Pageable pageable);

    /**
     * 按照时间区间查询
     * @param start java.sql.Date
     * @param end java.sql.Date
     * @return  List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByTimeBetween(Date start,Date end);

    /**
     * 按照时间区间查询，分页和排序
     * @param start java.sql.Date
     * @param end java.sql.Date
     * @param pageable Pageable
     * @return   Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByTimeBetween(Date start,Date end, Pageable pageable);

    /**
     * 按照公告来源
     * @param from String 来源
     * @return  List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByFrom(String from);

    /**
     * 按照公告来源
     * @param from String 来源
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByFrom(String from, Pageable pageable);

    /**
     * 按照公告类型，模糊
     * @param typeLike String
     * @return List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByTypeLike(String typeLike);

    /**
     * 按照公告类型，模糊
     * @param typeLike String 类型
     * @param pageable Pageable
     * @return  Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByTypeLike(String typeLike, Pageable pageable);

    /**
     * 按照发布作者查询 模糊
     * @param authorLike 作者
     * @return List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByAuthorLike(String authorLike);

    /**
     * 按照发布作者查询  模糊 分页 排序
     * @param authorLike 作者 String
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByAuthorLike(String authorLike, Pageable pageable);

    /**
     * 公告标题 模糊
     * @param titleLike 公告标题
     * @return List<ScisAnnouncement>
     */
    List<ScisAnnouncement> findByTitleLike(String titleLike);

    /**
     * 公告标题 模糊 分页 排序
     * @param titleLike 公告标题
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */
    Page<ScisAnnouncement> findByTitleLike(String titleLike, Pageable pageable);
}
