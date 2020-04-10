package werls.scis.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisCollege;
import werls.scis.service.CollegeServiceImpl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.admin
 * @Description: TODO
 * @date Date : 2020年04月10日 21:30
 */
@RestController
@RequestMapping("/admin/college")
public class CollegeAdminController {
    @Autowired
    CollegeServiceImpl service;

    @PostMapping("/repeat")
    public Map<String, Object> findByName(@RequestParam("name") String name){
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisCollege> college=service.findByCollegeName(name);
        if (college.isPresent()){
            res.put("data","true");
        }else {
            res.put("data","false");
        }
        return res;
    }
}
