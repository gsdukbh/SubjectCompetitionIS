package werls.scis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import werls.scis.dao.jpa.AnnouncementJpaRepository;
import werls.scis.dao.pojo.ScisAnnouncement;

import javax.sound.midi.SoundbankResource;
import javax.xml.ws.soap.Addressing;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 15:07
 */
@SpringBootTest
class AnnouncementServiceImplTest {
    @Autowired
    private  AnnouncementJpaRepository jpaRepository;

    @Test
    void findByTimeAfter() {
        Pageable pageable1= PageRequest.of(0, 2, Sort.by("time"));
        Date date=new Date();
        java.sql.Date date1=new java.sql.Date(date.getTime());
        System.out.println(jpaRepository.findByTimeAfter(date1,pageable1).getContent().toString());


    }

    @Test
    void findByTime() {
        Pageable pageable1= PageRequest.of(0, 2, Sort.by("time"));
        Date date=new Date();
        Calendar c1 = Calendar.getInstance();
        c1.set(2009, Calendar.JULY,25);
        java.sql.Date date1=new java.sql.Date(c1.getTime().getTime());
        System.out.println(jpaRepository.findByTime(date1,pageable1).getContent().toString());
    }

    @Test
    void findByTimeBefore() {
    }

    @Test
    void findByTimeBetween() {

    }

    @Test
    void findByFrom() {
    }

    @Test
    void findByTypeLike() {
    }

    @Test
    void findByAuthorLike() {
    }

    @Test
    void findByTitleLike() {
    }
}