package werls.scis.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import werls.scis.dao.pojo.ScisUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
/*    @Autowired
    VerificationCode code;*/
    @Test
    void code() {
        int [] array = new int[50];
        System.out.println(this.Fibonacci(5));;
    /*    System.out.println(code.code());
        System.out.println(code.createImage().toString());*/
    }
    public int Fibonacci(int n) {
        return Fibonacci1(n);
    }

    public int Fibonacci1(int n) {
        if (n < 1) {
            return 0;
        } else if (n < 3) {
            return 1;
        } else {
            return Fibonacci1(n - 1) + Fibonacci1(n + 2);
        }
    }

    @Test
    void text() {
        Set<String> strings = new HashSet<>();
        System.out.println(strings.add("s"));
        System.out.println(strings.add("s"));
        System.out.println(strings.add("s"));
        System.out.println(strings.add("s"));

        Set<ScisUser> userSet = new HashSet<>();
        ScisUser user = new ScisUser();
        user.setIdentity("1352115");
        System.out.println(userSet.add(user));
        System.out.println(userSet.add(user));
        System.out.println(userSet.add(user));
        System.out.println(userSet.add(user));
    }
}