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
 * @date Date : 2020年04月17日 22:35
 */
@SpringBootTest
class ApplyFromRepositoryTest {
    @Autowired
    ApplyFromRepository applyFromRepository;

    @Test
    void findScisUserIdA() {

        System.out.println(applyFromRepository.findScisUserIdA(2));
    }

}