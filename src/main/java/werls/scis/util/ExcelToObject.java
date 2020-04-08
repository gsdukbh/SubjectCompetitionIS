package werls.scis.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import werls.scis.dao.pojo.*;
import werls.scis.service.ClassServiceImpl;
import werls.scis.service.CollegeServiceImpl;
import werls.scis.service.MajorServiceImpl;
import werls.scis.service.UserServiceImpl;
import werls.scis.webSocket.WebSocket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月06日 22:06
 */

public class ExcelToObject extends AnalysisEventListener<UserUpObject> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final int BATCH_COUNT = 100;

    List<UserUpObject> list = new ArrayList<>();

    int i = 0;
    Integer role;

    UserServiceImpl userService;

    ClassServiceImpl classService;

    MajorServiceImpl majorService;

    CollegeServiceImpl collegeService;
    Integer adminUser;

    WebSocket webSocket;

    public ExcelToObject(UserServiceImpl userService) {
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
                         UserServiceImpl userService
    ) {
        this.webSocket = webSocket;
        this.adminUser = adminUser;
        this.userService = userService;
        this.role = role;
    }

    public ExcelToObject(Integer adminUser,
                         Integer role,
                         WebSocket webSocket,
                         UserServiceImpl userService,
                         ClassServiceImpl classService,
                         MajorServiceImpl majorService,
                         CollegeServiceImpl collegeService) {
        this.webSocket = webSocket;
        this.adminUser = adminUser;
        this.userService = userService;
        this.role = role;
        this.classService = classService;
        this.majorService = majorService;
        this.collegeService = collegeService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param userUpObject    UserUpObject
     * @param analysisContext AnalysisContext
     */
    @Override
    public void invoke(UserUpObject userUpObject, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(userUpObject));
        list.add(userUpObject);
        if (list.size() >= BATCH_COUNT) {
            save(list);
            list.clear();
        }
        i++;
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("所有数据解析完成！");

    }

    public void save(List<UserUpObject> userUpObject) {
        try {

            for (UserUpObject object : userUpObject) {
                Optional<ScisClass> scisClass = classService.findByName(object.getClassName());
                ScisClass tempClass = new ScisClass();
                ScisMajor temMajor = new ScisMajor();
                ScisCollege temCollege = new ScisCollege();
                ScisUser user = new ScisUser();
                ScisRole role = new ScisRole();
                List<ScisRole> roleList = new ArrayList<>();
                role.setId(this.role);
                roleList.add(role);
                if (this.role == 2) {
                    user.setRole("学生");
                } else if (this.role == 3) {
                    user.setRole("教师");
                }
                user.setPassword(object.getIdentity().substring(object.getIdentity().length() - 7));
                user.setLogin(object.getLogin());
                user.setName(object.getName());
                user.setSex(object.getSex());
                user.setEmail(object.getEmail());
                user.setIdentity(object.getIdentity());
                user.setPhone(object.getPhone());
                user.setStatus("false");
                user.setRoles(roleList);

                if (scisClass.isPresent()) {
                    tempClass = scisClass.get();
                    if (tempClass.getMajor() != null) {
                        temMajor = tempClass.getMajor();
                        if (temMajor.getCollege() != null) {
                            temCollege = temMajor.getCollege();
                            user.setScisClass(tempClass);
                        } else {
                            /*没用专业对应的学院，创建一个*/
                            temCollege.setName(object.getCollege());
                            temCollege = collegeService.save(temCollege);
                            /*把学院绑定到到专业上*/
                            temMajor.setCollege(temCollege);
                        }
                        /*有专业*/
                        tempClass.setMajor(temMajor);
                    } else {
                        /*没用对应专业,创建一个,*/
                        Optional<ScisCollege> college = collegeService.findByCollegeName(object.getCollege());
                        if (college.isPresent()) {
                            temCollege = college.get();
                        }else {
                            temCollege.setName(object.getCollege());
                            temCollege=collegeService.save(temCollege);
                        }
                        temMajor.setCollege(temCollege);
                        temMajor.setName(object.getMajorName());
                        temMajor.setLevel(object.getLevel());
                        temMajor = majorService.save(temMajor);
                        tempClass.setMajor(temMajor);
                    }

                } else {
                    /*没用班级信息*/
                    Optional<ScisCollege> college = collegeService.findByCollegeName(object.getCollege());
                    if (college.isPresent()) {
                        temCollege = college.get();

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ssssss");
        }

    }
}


