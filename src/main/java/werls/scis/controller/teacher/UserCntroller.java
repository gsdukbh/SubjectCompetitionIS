package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserService;
import werls.scis.service.UserServiceImpl;
import werls.scis.util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年04月01日 15:18
 */
@RestController
@RequestMapping("/tea/user")
public class UserCntroller {

    @Autowired
    UserService service;

    @Autowired
    Tools tools;

    @GetMapping("/findByRoleName/teacher")
    public Map<String, Object> findByRoleName() {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<Map<String, Object>> ret = new ArrayList<>();
        String name = "教师";
        res.put("status", 200);
        res.put("message", "Success");
        List<ScisUser> users = service.findByRoleName(name);
        for (ScisUser user : users) {
            Map<String, Object> type = new ConcurrentHashMap<>(1);
            String college = (user.getCollege().getName() == null || user.getCollege().getName().isEmpty()) ? "" : user.getCollege().getName();
            String getName = (user.getName() == null || user.getName().isEmpty() ? "" : user.getName());
            String value = getName + "-" + college;
            type.put("value", value);
            type.put("id", user.getId());
            type.put("name", getName);
            ret.add(type);
        }
        res.put("userInfo", ret);
        return res;
    }



    @GetMapping("/find/name")
    public Map<String, Object> findName() {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<String> list = service.findName();
        List<Map<String, Object>> ret = new ArrayList<>();
        res.put("message", "Success");
        res.put("status", 200);
        res.put("totalElements", list.size());
        return tools.getStringObjectMap(res, ret, list);
    }
}

