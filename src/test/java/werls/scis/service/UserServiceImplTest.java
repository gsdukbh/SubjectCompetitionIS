package werls.scis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import werls.scis.dao.pojo.ScisUser;

import javax.xml.ws.soap.Addressing;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年03月06日 11:08
 */
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;

    @Test
    void save() {
        ScisUser user= new ScisUser();
        user.setLogin("1121212121");
        userService.save(user);
    }

    @Test
    void findAll() {
        String s="123123";
        System.out.println(s.substring(0,3)+"sss"+s.substring(3));
    }
}