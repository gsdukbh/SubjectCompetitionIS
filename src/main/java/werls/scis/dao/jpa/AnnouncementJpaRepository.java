package werls.scis.dao.jpa;

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

    List<ScisAnnouncement> findByTimeIsAfter(Date timeAfter);

    List<ScisAnnouncement> findByTimeBefore(Date timeBefore);

    List<ScisAnnouncement> findByTime(Date date);


}
