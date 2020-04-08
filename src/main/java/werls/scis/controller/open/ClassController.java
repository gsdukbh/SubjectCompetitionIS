package werls.scis.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.open
 * @Description: TODO
 * @date Date : 2020年04月04日 16:30
 */
@RestController
@RequestMapping("/public/class")
public class ClassController {
    @Autowired
    ClassServiceImpl service;
    @GetMapping("/find/info")
    public Map<String,Object> findClassName(){
        Map <String, Object> res = new ConcurrentHashMap<>(10);
        List<ScisClass> classList=service.findAll();
        res.put("status", 200);
        res.put("message", "Success");
        res.put("data",classList);
        res.put("totalElements",classList.size());
        return res;
    }

    @GetMapping("/find")
    public Map<String,Object> findClass(){
        Map <String, Object> res = new ConcurrentHashMap<>(10);

        res.put("status", 200);
        res.put("message", "Success");
        Optional<ScisClass> tem=service.findByName("软件一般");
        tem.ifPresent(scisClass -> res.put("data", scisClass));

        return res;
    }
}
