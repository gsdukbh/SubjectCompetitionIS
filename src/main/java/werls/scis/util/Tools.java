package werls.scis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.dao.pojo.ScisCollege;
import werls.scis.dao.pojo.ScisMajor;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ClassServiceImpl;
import werls.scis.service.CollegeServiceImpl;
import werls.scis.service.MajorServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
    @Autowired
    ClassServiceImpl classService;
    @Autowired
    MajorServiceImpl majorService;
    @Autowired
    CollegeServiceImpl collegeService;

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
        scisMajor = major.map(value -> isMajor(value, object)).orElseGet(() -> noMajor(object));
        tem.setMajor(scisMajor);
        tem = classService.save(tem);
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

