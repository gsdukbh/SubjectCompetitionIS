package werls.scis.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import werls.scis.service.CompetitionServiceImpl;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月17日 11:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
class CompetitionAdminTest {

    @Autowired
    CompetitionServiceImpl competitionService;

    @Autowired
    private WebApplicationContext wac;

//    private MockMvc mockMvc;

//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }

    @Test
    void findById() throws Exception {
        MockMvc  mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/public/competition/findById/5")
                .accept(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.status().isOk())             //等同于Assert.assertEquals(200,status);
                .andDo(MockMvcResultHandlers.print())

                .andReturn();

    }
}