package werls.scis.controller.student;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserService;
import werls.scis.service.UserServiceImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月04日 16:55
 */
@RestController
@RequestMapping("/i")
public class MyInfo {
    @Autowired
    UserService userService;


    @PostMapping("/rest/info")
    public Map<String, Object> restPwd(@RequestBody JSONObject user) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisUser> userOptional = userService.findById(user.getInteger("id"));
        if (userOptional.isPresent()) {
            ScisUser tem = userOptional.get();
            tem.setPassword(user.getString("password"));
            res.put("status", 200);
            res.put("data", userService.save(tem));
        } else {
            res.put("status", 403);
        }
        return res;
    }

    @PostMapping("/upData/info")
    public Map<String, Object> upData(@RequestBody ScisUser user) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisUser> userOptional = userService.findById(user.getId());
        if (userOptional.isPresent()) {
            user.setPassword(userOptional.get().getPassword());
            res.put("status", 200);
            user.setApplyFroms(userOptional.get().getApplyFroms());
            user.setStatus("true");
            res.put("data", userService.upData(user));
        } else {
            res.put("status", 403);
        }
        return res;
    }

    @PostMapping("/email/repeat")
    public Map<String, Object> finByEmail(@RequestParam String email) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        Optional<ScisUser> user = userService.findByEmail(email);
        if (user.isPresent()) {
            res.put("SID", user.get().getLogin());
            res.put("data", "true");
        } else {
            res.put("data", "false");
        }
        return res;
    }

    @GetMapping("/findMyInfo/{id}")
    public Map<String, Object> findMyInfo(@PathVariable Integer id) {
        Map<String, Object> res = new HashMap<>(16);
        Optional<ScisUser> user = userService.findById(id);
        if (user.isPresent()) {
            res.put("status", 200);
            res.put("data", user.get());
        } else {
            res.put("status", 404);
        }
        return res;
    }

    @PostMapping(value = "/i")
    public Map<String, Object> getUserInfo(@Param("info") String info) {
        ScisUser user = this.userService.findByLoginOrPhoneOrIdentityOrEmail(info);
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        if (user == null) {
            res.put("status", 404);
            res.put("message", "null");
        } else {
            res.put("status", 200);
            res.put("message", "success");
            res.put("data", user);
            List<String> role = new ArrayList<>();
            for (ScisRole scisRole : user.getRoles()) {
                role.add(scisRole.getAuthority());
            }

            res.put("role", role);
            user.setPassword("");
            user.setRoles(null);
        }
        return res;
    }


}
