package werls.scis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.EmailServiceImpl;
import werls.scis.service.UserServiceImpl;
import werls.scis.util.EmailTemplate;
import werls.scis.util.VerificationCode;

import javax.xml.ws.soap.Addressing;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
public class Password {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserServiceImpl userService;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    EmailTemplate emailTemplate;

    @Autowired
    VerificationCode code;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/1")
    public String te(@RequestParam("me") String test ,@RequestParam("sdddd") String sd ) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", test + sd);
        return JSON.toJSONString(res);
    }
    @GetMapping(value = "/kk")
    public String test(@RequestBody JSONObject jsonParam ) {

        return JSON.toJSONString(jsonParam);
    }

    /**
     * 邮箱重置
     *
     * @param email 邮箱
     * @return JSON
     */
    @PostMapping(value = "/password/recover/email")
    public String pwdRecoverByEmail(@RequestParam("email") String email) {
        Map<String, Object> res = new ConcurrentHashMap<>();
        ScisUser user = userService.findByLoginOrPhoneOrIdentityOrEmail(email);
        if (user == null) {
            res.put("status", 404);
            res.put("message", "该邮箱没有绑定用户，请请更换方式");
            return JSON.toJSONString(res);
        } else {
            /*发送送验证码*/
            res.put("status", 200);
            String send = code.code();
            /*redis 保存验证码 5分钟*/
            redisTemplate.opsForValue().set(email, send, 5, TimeUnit.MINUTES);
            /*发送验证码*/
            emailService.sendHtmlEmail(user.getEmail(),
                    "密码重置",
                    emailTemplate.sandCode(send,
                            "密码重置"));
            res.put("message", "验证已经发送，5分钟内有效，注意查看邮箱");
            return JSON.toJSONString(res);
        }
    }

    @PostMapping(value = "/password/recover/email/code")
    public String pwdReByEmailCode(@Param("email") String email,
                                   @Param("code") String code,
                                   @Param("password") String password) {
        Map<String, Object> res = new ConcurrentHashMap<>();
        String redisCode = redisTemplate.opsForValue().get(email);
        if (redisCode == null) {
            res.put("status", 404);
            res.put("message", "验证码无效，重新输入");
            return JSON.toJSONString(res);
        } else if (redisCode.equals(code)) {
            res.put("status", 200);
            res.put("message", "密码修改成功");
            ScisUser user = userService.findByLoginOrPhoneOrIdentityOrEmail(email);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            logger.info(user.getName() + "重置了密码");
            userService.save(user);
            return JSON.toJSONString(res);
        } else {
            res.put("status", 403);
            res.put("message", "验证码错误");
            return JSON.toJSONString(res);
        }
    }

    /*其他方式使用手机验证码重置*/

    public String pwdReByPhone(@Param("phone") String phone) {
        Map<String, Object> res = new HashMap<>();
        ScisUser user = userService.findByLoginOrPhoneOrIdentityOrEmail(phone);
        if (user == null) {
            res.put("status", 404);
            res.put("message", "没有该用户");
            return JSON.toJSONString(res);
        } else {
            res.put("status", 200);
            /*通过手机发送验证码*/
            return JSON.toJSONString(res);
        }
    }

    @PostMapping(value = "/password/recover/find")
    @Cacheable(value = "pwdReByAny",key = "#name",unless = "#result == null ")
    public String pwdReByAny(@Param("name") String name) {
        Map<String, Object> res = new HashMap<>();
        ScisUser user = userService.findByLoginOrPhoneOrIdentityOrEmail(name);
        if (user == null) {
            res.put("status", 404);
            res.put("message", "没有该用户");
            logger.info("正在查询："+name);
            return JSON.toJSONString(res);
        } else {
            res.put("status", 200);
            res.put("message", "有该用户");
            String email = user.getEmail() == null ? null : user.getEmail().substring(0, 3) +
                    "****" +
                    user.getEmail().substring(7);
            String phone = user.getPhone() == null ? null : user.getPhone().substring(0, 3) +
                    "****" +
                    user.getPhone().substring(7);
            res.put("email", email);
            res.put("phone", phone);
            logger.info("查询："+name+"成功");
            return JSON.toJSONString(res);
        }
    }
}
