package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserServiceImpl;

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
    private UserRepository repository;
    @Test
    void findByLogin() {
        boolean user1=repository.findById(1).isPresent();
        ScisUser user=repository.findById(1).get();
        user.setName("我的世界");
        repository.save(user);
        System.out.println(repository.findAll().toString());
    }
}