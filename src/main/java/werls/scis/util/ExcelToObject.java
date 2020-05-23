package werls.scis.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import werls.scis.dao.pojo.*;
import werls.scis.service.*;
import werls.scis.webSocket.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月06日 22:06
 */

public class ExcelToObject extends AnalysisEventListener<UserUpObject> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int BATCH_COUNT = 3000;

    List<UserUpObject> list = new CopyOnWriteArrayList<>();
//    List<UserUpObject> list = new ArrayList<>();

    int i = 0;
    Integer role;

    UserService userService;

    Tools tools;

    ClassServiceImpl classService;

    MajorServiceImpl majorService;

    CollegeServiceImpl collegeService;

    Integer adminUser;

    WebSocket webSocket;

    public ExcelToObject(UserService userService) {
        this.userService = userService;
    }

    public ExcelToObject(Integer adminUser) {
        this.adminUser = adminUser;
    }

    public ExcelToObject() {

    }

    public ExcelToObject(Integer adminUser, WebSocket webSocket) {
        this.webSocket = webSocket;
        this.adminUser = adminUser;
    }

    public ExcelToObject(Integer adminUser,
                         Integer role,
                         WebSocket webSocket,
                         Tools tools
    ) {
        this.webSocket = webSocket;
        this.adminUser = adminUser;
        this.role = role;
        this.tools = tools;
    }

    public ExcelToObject(Integer adminUser,
                         Integer role,
                         WebSocket webSocket,
                         UserService userService,
                         ClassServiceImpl classService,
                         MajorServiceImpl majorService,
                         CollegeServiceImpl collegeService,
                         Tools tools) {
        this.webSocket = webSocket;
        this.adminUser = adminUser;
        this.userService = userService;
        this.role = role;
        this.classService = classService;
        this.majorService = majorService;
        this.collegeService = collegeService;
        this.tools = tools;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param userUpObject    UserUpObject
     * @param analysisContext AnalysisContext
     */

    @Override
    public void invoke(UserUpObject userUpObject, AnalysisContext analysisContext) {
//        logger.info("解析到一条数据:{}", JSON.toJSONString(userUpObject));
        list.add(userUpObject);
        if (list.size() >= BATCH_COUNT) {
            tools.saveUserInfo(new ArrayList<>(list), this.role, adminUser);
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        tools.saveUserInfo(list, this.role, adminUser);
        logger.info("所有数据解析完成！");
    }

//    private void sendWebsocket(UserUpObject userUpObject) {
//        webSocket.sendOneMessage(adminUser.toString(), JSON.toJSONString(userUpObject));
//    }

//    @Async
//    public void save(List<UserUpObject> userUpObject) {
//        try {
//            for (UserUpObject object : userUpObject) {
//                ScisUser user = new ScisUser();
//                ScisRole role = new ScisRole();
//                List<ScisRole> roleList = new ArrayList<>();
//                role.setId(this.role);
//                roleList.add(role);
//                if (this.role == 2) {
//                    user.setRole("学生");
//                } else if (this.role == 3) {
//                    user.setRole("教师");
//                }
//                user.setPassword(object.getIdentity() != null ? object.getIdentity().substring(object.getIdentity().length() - 6) : "123456");
//                user.setLogin(object.getLogin());
//                user.setName(object.getName());
//                user.setSex(object.getSex());
//                user.setEmail(object.getEmail());
//                user.setIdentity(object.getIdentity());
//                user.setPhone(object.getPhone());
//                user.setStatus("false");
//                user.setRoles(roleList);
//
//                /*查找是否有该班级信息*/
//                Optional<ScisClass> scisClass = classService.findByName(object.getClassName());
//                Optional<ScisCollege> scisCollege = collegeService.findByCollegeName(object.getCollege());
//                if (this.role == 2) {
//                    if (scisClass.isPresent()) {
//                        tools.isClass(user, scisClass.get(), object);
//                    } else {
//                        tools.noClass(user, object);
//                    }
//                } else {
//                    ScisCollege temCollege = new ScisCollege();
//                    temCollege = scisCollege.map(tools::isCollege).orElseGet(() -> tools.noCollege(object));
//                    user.setCollege(temCollege);
//                }
//                userService.save(user);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//    }

}
