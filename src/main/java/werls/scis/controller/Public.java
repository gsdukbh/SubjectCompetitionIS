package werls.scis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserServiceImpl;

import javax.xml.ws.soap.Addressing;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月04日 15:19
 */
@RestController
@RequestMapping("/public")
public class Public {
    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/1")
    public String te(){
        Map<String,Object> res= new HashMap<>();
        res.put("code",200);
        res.put("message", "success");
        return JSON.toJSONString(res);
    }
}
