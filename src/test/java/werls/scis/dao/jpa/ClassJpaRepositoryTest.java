package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.AccessType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年04月08日 19:39
 */
@SpringBootTest
class ClassJpaRepositoryTest {
    @Autowired
    ClassJpaRepository classJpaRepository;

    @Test
    void findByName() {
        System.out.println(classJpaRepository.findByName("sssss"));
    }

    @Test
    void testFindByName() {
    }

    @Test
    void findByNameLike() {
    }
}