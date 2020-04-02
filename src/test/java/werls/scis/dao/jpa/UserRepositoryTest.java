package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserServiceImpl;
import werls.scis.util.RedisKeyUtil;
import werls.scis.util.RedisService;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月27日 10:06
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UserRepositoryTest {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
   UserRepository repository;

    @Test
    void findByLogin() {
        ScisUser userVo = new ScisUser();
        TimeUnit timeUnit;
        System.out.println(TimeUnit.HOURS.toSeconds(1));
        redisTemplate.opsForValue().set("wo", "12121", 10, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("wo"));

    }

    @Test
    void findByRole() {
        Pageable pageable1= PageRequest.of(0, 20);
        System.out.println(repository.findByRoleName("教师",pageable1).getContent().get(0).getName());

    }

    @Test
    void findByRoleName() {
    }
}