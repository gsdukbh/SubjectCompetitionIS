package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年03月10日 12:14
 */

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;
    @Test
    void findByName() {
        System.out.println(roleRepository.findByName("超级管理").getScisUserList());
    }
}