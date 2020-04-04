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
import java.util.Iterator;
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
    private  AnnouncementServiceImpl jpaRepository;

    Pageable pageable1= PageRequest.of(0, 20, Sort.by("time"));
    @Test
    void findByTimeAfter() {

        Date date=new Date();
        java.sql.Date date1=new java.sql.Date(date.getTime());
        System.out.println(jpaRepository.findByTimeAfter(date1,pageable1).getContent().toString());


    }

    @Test
    void findByTime() {
        Date date=new Date();
        Calendar c1 = Calendar.getInstance();
        Date n=c1.getTime();
        c1.add(Calendar.HOUR,2);
        System.out.println(n);
        System.out.println(c1.getTime().toString());
        c1.set(2009, Calendar.JULY,25);
        System.out.println(c1.getTime());
//        java.sql.Date date1=new java.sql.Date(c1.getTime().getTime());
//        System.out.println(jpaRepository.findByTime(date1,pageable1).getContent().toString());
    }



    @Test
    void findByTitleLike() {
        Iterator<ScisAnnouncement> announcementIterator=jpaRepository.findByTitleContainingOrTypeContainingOrFrom("","","",pageable1).getContent().iterator();

        System.out.println();
    }

    @Test
    void findAll() {
        System.out.println(jpaRepository.findAll(pageable1).getContent().get(1));
    }

    @Test
    void testFindByTypeLike() {
        System.out.println(jpaRepository.findType().toString());
    }
}