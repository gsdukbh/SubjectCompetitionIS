package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.AnnouncementJpaRepository;
import werls.scis.dao.pojo.ScisAnnouncement;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 14:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AnnouncementServiceImpl {

    @Autowired
    private AnnouncementJpaRepository announcementJpaRepository;


    /**
     * 公告时间在此之后
     *
     * @param timeAfter 时间 java.sql.Date
     * @param pageable  Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByTimeAfter(Date timeAfter, Pageable pageable) {
        return announcementJpaRepository.findByTimeAfter(timeAfter, pageable);
    }

    /**
     * 公告当天时间
     *
     * @param date     java.sql.Date
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByTime(Date date, Pageable pageable) {
        return announcementJpaRepository.findByTime(date, pageable);
    }

    /**
     * 公告时间之前
     *
     * @param timeBefore java.sql.Date
     * @param pageable   Pageable
     * @return List<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByTimeBefore(Date timeBefore, Pageable pageable) {
        return announcementJpaRepository.findByTimeBefore(timeBefore, pageable);
    }

    /**
     * 按照时间区间查询，分页和排序
     *
     * @param start    java.sql.Date
     * @param end      java.sql.Date
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByTimeBetween(Date start, Date end, Pageable pageable) {
        return announcementJpaRepository.findByTimeBetween(start, end, pageable);
    }

    /**
     * 按照公告来源
     *
     * @param from     String 来源
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByFrom(String from, Pageable pageable) {
        return announcementJpaRepository.findByFrom(from, pageable);
    }

    /**
     * 按照公告类型，模糊 排序 分页
     *
     * @param typeLike String 类型
     * @param pageable Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByTypeContaining(String typeLike, Pageable pageable) {
        return announcementJpaRepository.findByTypeContaining(typeLike, pageable);
    }

    /**
     * 按照发布作者查询  模糊 分页 排序
     *
     * @param authorLike 作者 String
     * @param pageable   Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByAuthorLike(String authorLike, Pageable pageable) {
        return announcementJpaRepository.findByAuthorContaining(authorLike, pageable);
    }

    /**
     * 公告标题 模糊 分页 排序
     *
     * @param titleLike 公告标题
     * @param pageable  Pageable
     * @return Page<ScisAnnouncement>
     */

    public Page<ScisAnnouncement> findByTitleContaining(String titleLike, Pageable pageable) {
        return announcementJpaRepository.findByTitleContaining(titleLike, pageable);
    }

    /**
     * 必须包含完整的信息
     *
     * @param scisAnnouncement ScisAnnouncement
     * @return ScisAnnouncement
     */

    public ScisAnnouncement save(ScisAnnouncement scisAnnouncement) {
        return announcementJpaRepository.save(scisAnnouncement);
    }

    public List<ScisAnnouncement> save(List<ScisAnnouncement> announcementList) {
        return announcementJpaRepository.saveAll(announcementList);
    }

    public Page<ScisAnnouncement> findAll(Pageable pageable) {
        return announcementJpaRepository.findAll(pageable);
    }


    public void delete(ScisAnnouncement announcement) {
        this.announcementJpaRepository.delete(announcement);
    }

    public void deleteAll(List<ScisAnnouncement> announcement) {
        this.announcementJpaRepository.deleteAll(announcement);
    }

    public void deleteById(Integer id) {
        this.announcementJpaRepository.deleteById(id);
    }

    /**
     * 查找公告类型 ，
     *
     * @return List<String>
     */
    public List<String> findType() {
        return announcementJpaRepository.findType();
    }

    public Page<ScisAnnouncement> findByTitleContainingAndTypeContainingAndFrom(
            String title,
            String type,
            String from,
            Pageable pageable) {
        return announcementJpaRepository.findByTitleContainingAndTypeContainingAndFrom(title, type, from, pageable);
    }

    public Page<ScisAnnouncement> findByTitleContainingOrTypeContainingOrFrom(
            String title,
            String type,
            String from,
            Pageable pageable) {
        return announcementJpaRepository.findByTitleContainingOrTypeContainingOrFrom(title, type, from, pageable);
    }

    public Page<ScisAnnouncement> findByTitleContainingAndTypeContaining(
            String title,
            String type,
            Pageable pageable) {
        return announcementJpaRepository.findByTitleContainingAndTypeContaining(title, type, pageable);
    }

    public Page<ScisAnnouncement> findByTitleContainingAndFrom(String title, String type, Pageable pageable) {
        return announcementJpaRepository.findByTitleContainingAndFrom(title, type, pageable);
    }

    public Page<ScisAnnouncement> findByTypeContainingAndFrom(String type, String from, Pageable pageable) {
        return announcementJpaRepository.findByTypeContainingAndFrom(type, from, pageable);
    }
    public Optional<ScisAnnouncement> findById(Integer id){

        return announcementJpaRepository.findById(id);
    }
    public   List<String> findTitle(){
        return announcementJpaRepository.findTitle();
    }
}
