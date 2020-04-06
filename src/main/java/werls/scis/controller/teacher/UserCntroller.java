package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisUser;
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
    UserServiceImpl service;

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
            ret.add(type);
        }
        res.put("userInfo", ret);
        return res;
    }

    @PostMapping("/find/student")
    public Map<String, Object> findStudent(@RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String college = jsonObject.getString("college");
        String major = jsonObject.getString("major");
        String name = jsonObject.getString("name");
        String className = jsonObject.getString("className");
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("size");
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        if (college != null && major != null && name != null && className != null) {
            List<Map<String, Object>> mapList = service.findNameAndClassNameAndMajorNameAndCollegeName(
                    name,
                    className,
                    major,
                    college,
                    page,
                    size);
            return getStringObjectMap(res, mapList);
        } else if (college != null && major != null) {
            Page<ScisUser> tem = service.findByScisClassMajorCollegeNameAndScisClassMajorNameAndRole(college, major, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (college != null && name != null) {
            List<Map<String, Object>> mapList = service.findByInfoAndCollege(name, college, "学生", page, size);
            return getStringObjectMap(res, mapList);
        } else if (college != null && className != null) {
            Page<ScisUser> tem = service.findByScisClassMajorCollegeNameAndScisClassNameAndRole(college, className, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (major != null && name != null) {
            List<Map<String, Object>> mapList = service.findByInfoAndMajorName(name, major, page, size);
            return getStringObjectMap(res, mapList);
        } else if (major != null && className != null) {
            Page<ScisUser> tem = service.findByScisClassMajorNameAndScisClassNameAndRole(major, className, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (name != null && className != null) {
            List<Map<String, Object>> mapList = service.findByClassNameAndName(name, className, page, size);
            return getStringObjectMap(res, mapList);
        } else if (college != null) {
            Page<ScisUser> tem = service.findByCollegeNameAndRole(college, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (major != null) {
            Page<ScisUser> tem = service.findByScisClassMajorNameAndRole(major, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (name != null) {
            List<Map<String, Object>> mapList = service.findByNameOrLoginOrIdentityAndRole(name, "学生", page, size);
            if (mapList != null && !mapList.isEmpty()) {
                res.put("totalElements", mapList.get(0).get("totalElements"));
                res.put("data", mapList);
                res.put("status", 200);
                res.put("special", 1);
                return res;
            }
            res.put("special", 0);
            res.put("status", 403);
            return res;
        } else if (className != null) {
            Page<ScisUser> tem = service.findByScisClassNameAndRole(className, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else {
            Page<ScisUser> tem = service.findByRole("学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        }
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> res, List<Map<String, Object>> mapList) {
        if (mapList != null && !mapList.isEmpty()) {
            res.put("totalElements", mapList.get(0).get("totalElements"));
            res.put("data", mapList);
            res.put("status", 200);
            res.put("special", 1);
            return res;
        }
        res.put("status", 403);
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

