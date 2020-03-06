package werls.scis.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年03月06日 10:28
 */
@SpringBootTest
class VerificationCodeTest {
    @Autowired
    VerificationCode code;
    @Test
    void code() {
        System.out.println(code.code());
        System.out.println(code.createImage().toString());
    }
}