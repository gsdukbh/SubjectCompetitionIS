package werls.scis.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.service.AnnouncementServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年03月15日 16:28
 */
@RestController
@RequestMapping("/tea")
public class AnnouncementController {
    @Autowired
    AnnouncementServiceImpl service;

    @PostMapping("/announcement/save")
    public String save(ScisAnnouncement announcement){
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        if (announcement != null){

        }

        return  null;
    }
}
