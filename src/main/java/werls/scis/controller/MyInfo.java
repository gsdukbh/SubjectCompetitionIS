package werls.scis.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    UserServiceImpl userService;

    @Cacheable(value = "uInfo",key = "#info",unless = "#result == null")
    @PostMapping(value = "/i")
    public String getUserInfo(@Param("info") String info) {
        ScisUser user = this.userService.findByLoginOrPhoneOrIdentityOrEmail(info);
        Map<String, Object> res = new HashMap<>();
        if (user == null) {
            res.put("code", 404);
            res.put("message", "null");
            return JSON.toJSONString(res);
        } else {
            res.put("code", 200);
            res.put("message", "success");
            res.put("data", user);
            List<String> role= new ArrayList<>();
            for (ScisRole scisRole: user.getRules() ){
                role.add(scisRole.getAuthority());
            }
//            res.put("avatar",user)  留坑 头像图片地址
            res.put("role",role);
            user.setPassword("");
            user.setRules(null);
            return JSON.toJSONString(res);
        }
    }


}
