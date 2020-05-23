package werls.scis.util;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import werls.scis.dao.pojo.*;
import werls.scis.service.*;
import werls.scis.webSocket.WebSocket;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月03日 22:39
 */
@Component
public class Tools {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ClassServiceImpl classService;
    @Autowired
    MajorServiceImpl majorService;
    @Autowired
    CollegeServiceImpl collegeService;
    @Autowired
    WorksServiceImpl worksService;
    @Autowired
    UserService userService;
    @Autowired
    ApplyFromSericeImpl applyFromSerice;

    @Autowired
    WebSocket webSocket;

    public List<ScisApplyFrom> addRank(List<ScisApplyFrom> applyFromList) {
        List<ScisApplyFrom> list = new ArrayList<>();
        for (ScisApplyFrom apply : applyFromList) {
            apply.setGradesanking(applyFromSerice.getGradeRank(apply.getId()).toString());
            list.add(apply);
        }
        return list;
    }


    public List<Map<String, Object>> getScoreLevel(Integer competitionId) {
        List<Map<String, Object>> score = new ArrayList<>(16);
        Map<String, Object> temp1 = new HashMap<>(16);
        Map<String, Object> temp2 = new HashMap<>(16);
        Map<String, Object> temp3 = new HashMap<>(16);
        Map<String, Object> temp4 = new HashMap<>(16);
        Map<String, Object> temp5 = new HashMap<>(16);
        temp1.put("name", "不合格");
        temp1.put("value", applyFromSerice.gradeDistribution(competitionId, 0, 59));

        temp2.put("name", "合格");
        temp2.put("value", applyFromSerice.gradeDistribution(competitionId, 60, 75));

        temp3.put("name", "中等");
        temp3.put("value", applyFromSerice.gradeDistribution(competitionId, 76, 85));


        temp4.put("name", "良好");
        temp4.put("value", applyFromSerice.gradeDistribution(competitionId, 86, 95));

        temp5.put("name", "优秀");
        temp5.put("value", applyFromSerice.gradeDistribution(competitionId, 96, 100));

        score.add(temp1);
        score.add(temp2);
        score.add(temp3);
        score.add(temp4);
        score.add(temp5);

        return score;
    }


    public String getFilename(HttpServletRequest request,
                              String filename) throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] iebrowserkeywords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : iebrowserkeywords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(filename, "UTF-8");
            }
        }

        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

    @Async
    public void saveScore(List<Score> scores, Integer competitionId, Integer userId) {
        try {
            logger.info("开始将成绩写入数据库");
            List<Score> list = new CopyOnWriteArrayList<>(scores);
            for (Score score : list) {
                ScisUser user = userService.findByLogin(score.getLogin());
                if (user != null) {
                    ScisApplyFrom apply = applyFromSerice.findByUserIdCompetitionId(user.getId(), competitionId);
                    if (apply != null) {
                        apply.setScisUser(user);
                        apply.setScore(score.getScore());
//                        apply.setGradesanking(score.getGrades());
                        if (apply.getWorks() != null) {
                            ScisWorks works = apply.getWorks();
                            works.setScore(score.getScore());
                            worksService.save(works);
                        }
                        applyFromSerice.save(apply);
                    } else {
                        logger.info("该学生没有报名：{}", score.getName());

                    }
                } else {
                    logger.info("不存在该学号的学生信息：{}", score.getLogin());
                    Map<String, Object> send = new HashMap<>();
                    send.put("messages", "不存在该学号的学生信息");
                    webSocket.sendOneMessage(userId.toString(), JSON.toJSONString(send));
                }
            }
            logger.info("数据导入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Async
    public void saveUserInfo(List<UserUpObject> userUpObject, Integer roleId, Integer adminUser) {
        try {
            logger.info("开始将信息写入数据库");
            List<UserUpObject> list = new ArrayList<>(userUpObject);
            for (UserUpObject object : list) {
                webSocket.sendOneMessage(adminUser.toString(), JSON.toJSONString(object));
                ScisUser user = new ScisUser();
                ScisRole role = new ScisRole();
                List<ScisRole> roleList = new ArrayList<>();
                role.setId(roleId);
                roleList.add(role);
                if (roleId == 2) {
                    user.setRole("学生");
                } else if (roleId == 3) {
                    user.setRole("教师");
                }
                user.setPassword(object.getIdentity() != null ? object.getIdentity().substring(object.getIdentity().length() - 6) : "123456");
                user.setLogin(object.getLogin());
                user.setName(object.getName());
                user.setSex(object.getSex());
                user.setEmail(object.getEmail());
                user.setIdentity(object.getIdentity());
                user.setPhone(object.getPhone());
                user.setStatus("false");
                user.setRoles(roleList);

                /*查找是否有该班级信息*/
                Optional<ScisClass> scisClass = classService.findByName(object.getClassName());
                Optional<ScisCollege> scisCollege = collegeService.findByCollegeName(object.getCollege());
                if (roleId == 2) {
                    if (scisClass.isPresent()) {
                        isClass(user, scisClass.get(), object);
                    } else {
                        noClass(user, object);
                    }
                } else {
                    ScisCollege temCollege;
                    temCollege = scisCollege.map(this::isCollege).orElseGet(() -> this.noCollege(object));
                    user.setCollege(temCollege);
                }
                if (userService.findByLogin(user.getLogin()) == null) {
                    userService.save(user);
                    logger.info("保存了用户信息：{}", user.getLogin() + "-" + user.getName());
                } else {
                    logger.info("用户信息：{},已存在", user.getLogin() + "-" + user.getName());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public Map<String, Object> getStringObjectMap(Map<String, Object> res, List<Map<String, Object>> ret, List<String> strings) {
        for (String string : strings) {
            Map<String, Object> type = new ConcurrentHashMap<>(1);
            if (string != null) {
                type.put("value", string);
            }
            ret.add(type);
        }
        res.put("data", ret);
        return res;
    }

    public void isClass(ScisUser user, ScisClass scisClass, UserUpObject object) {
        ScisMajor major = scisClass.getMajor();
        major = major != null ? isMajor(major, object) : noMajor(object);
        scisClass.setMajor(major);
        user.setScisClass(scisClass);
    }

    public void noClass(ScisUser user, UserUpObject object) {
        ScisClass tem = new ScisClass();
        ScisMajor scisMajor = new ScisMajor();
        tem.setName(object.getClassName());
        Optional<ScisMajor> major = majorService.findByMajorName(object.getMajorName());
//        scisMajor = major.map(value -> isMajor(value, object)).orElseGet(() -> noMajor(object));
        scisMajor = major.isPresent() ? isMajor(major.get(), object) : noMajor(object);
        tem.setMajor(scisMajor);
        tem = classService.save(tem);
        logger.info("创建了专业户信息：{}", tem.getName());
        user.setScisClass(tem);
    }

    public ScisMajor isMajor(ScisMajor major, UserUpObject object) {
        ScisCollege college = major.getCollege();
        college = college != null ? isCollege(college) : noCollege(object);
        major.setCollege(college);
        return major;
    }

    public ScisMajor noMajor(UserUpObject object) {
        ScisMajor major = new ScisMajor();
        ScisCollege scisCollege = new ScisCollege();
        major.setLevel(object.getLevel());
        major.setName(object.getMajorName());
        Optional<ScisCollege> college = collegeService.findByCollegeName(object.getCollege());
        scisCollege = college.map(this::isCollege).orElseGet(() -> noCollege(object));
        major.setCollege(scisCollege);
        major = majorService.save(major);
        logger.info("创建了专业户信息：{}", major.getName());
        return major;
    }

    public ScisCollege isCollege(ScisCollege college) {
        return college;
    }

    public ScisCollege noCollege(UserUpObject object) {
        ScisCollege college = new ScisCollege();
        college.setName(object.getCollege());
        college = collegeService.save(college);
        return college;
    }
}

