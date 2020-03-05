package werls.scis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import werls.scis.util.EmailTemplate;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年03月05日 21:57
 */
@SpringBootTest
class EmailServiceImplTest {

    @Autowired
    private  EmailServiceImpl service;
    @Autowired
    EmailTemplate s;

    @Test
    void sendTextEmail() {
        service.sendTextEmail("1361404576@qq.com","这是一个测试邮箱","内容：发送成功");
    }

    @Test
    void sendHtmlEmail() {
        System.out.println(s.sandCode("12","ss"));
        service.sendHtmlEmail("1361404576@qq.com","这是一个测试邮箱",s.sandCode("4396","测试"));
    }
}