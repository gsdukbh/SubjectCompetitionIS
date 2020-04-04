package werls.scis.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisMajor;
import werls.scis.service.MajorServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.open
 * @Description: TODO
 * @date Date : 2020年04月04日 16:57
 */
@RestController
@RequestMapping("/public/major")
public class MajorController {
    @Autowired
    MajorServiceImpl majorService;
    @GetMapping("/find")
    public Map<String,Object> findMajor(){
        Map <String, Object> res = new ConcurrentHashMap<>(10);
        res.put("status", 200);
        res.put("message", "Success");
        List<ScisMajor> majors=majorService.findByAll();
        res.put("data",majors);
        res.put("totalElements",majors.size());
        return res;
    }
}
