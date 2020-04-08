package werls.scis.controller.admin;

import com.alibaba.excel.EasyExcel;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ClassServiceImpl;
import werls.scis.service.CollegeServiceImpl;
import werls.scis.service.MajorServiceImpl;
import werls.scis.service.UserServiceImpl;
import werls.scis.util.ExcelToObject;
import werls.scis.util.UserUpObject;
import werls.scis.webSocket.WebSocket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.admin
 * @Description: TODO
 * @date Date : 2020年03月27日 14:51
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserServiceImpl service;
    @Autowired
    ClassServiceImpl classService;
    @Autowired
    MajorServiceImpl majorService;
    @Autowired
    CollegeServiceImpl collegeService;
    @Autowired
    WebSocket webSocket;

    @PostMapping("/save")
    public Map<String, Object> saveUser(@RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        ScisUser user = JSONObject.toJavaObject(jsonObject, ScisUser.class);
//        ScisUser tem = service.save(user);
//        res.put("data",tem);
        return res;
    }

    @PostMapping("/endowTea")
    public Map<String, Object> endowTea(@RequestBody ScisUser user) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisUser tem = service.findByLogin(user.getLogin());
        if (tem != null) {
            try {
                ScisRole role = new ScisRole();
                role.setId(3);
                List<ScisRole> list = new ArrayList<>();
                list.add(role);
                tem.setRoles(list);
                service.save(tem);
                res.put("status", 200);
                return res;
            } catch (Exception e) {
                res.put("status", 403);
                e.printStackTrace();
                return res;
            }
        } else {
            res.put("status", 403);
            return res;
        }
    }

    @PostMapping("/endowRoot")
    public Map<String, Object> endowRoot(@RequestBody ScisUser user) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisUser tem = service.findByLogin(user.getLogin());
        if (tem != null) {
            try {
                ScisRole role = new ScisRole();
                role.setId(1);
                List<ScisRole> list = tem.getRoles();
                list.add(role);
                tem.setRoles(list);
                service.save(tem);
                res.put("status", 200);
                return res;
            } catch (Exception e) {
                res.put("status", 403);
                e.printStackTrace();
                return res;
            }
        } else {
            res.put("status", 403);
            return res;
        }
    }

    @PostMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(@RequestBody ScisUser user) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisUser tem = service.findByLogin(user.getLogin());
        if (tem != null) {
            List<ScisRole> list = tem.getRoles();
            res.put("data", list);
            res.put("status", 200);
            return res;
        } else {
            res.put("status", 403);
            return res;
        }
    }

    @PostMapping("/find/tea")
    public Map<String, Object> findTea(@RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String college = jsonObject.getString("college");
        String name = jsonObject.getString("name");
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("size");
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        if (college != null && name != null) {
            System.out.println(1);
            List<Map<String, Object>> mapList = service.findByInfoAndCollege(name, college, page, size);
            return getStringObjectMap(res, mapList);
        } else if (college != null) {
            System.out.println(2);
            Page<ScisUser> tem = service.findByCollegeName(college, pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (name != null) {
            System.out.println(3);
            List<Map<String, Object>> mapList = service.findByNameOrLoginOrIdentityAndRole(name, page, size);
            return getStringObjectMap(res, mapList);
        } else {
            Page<ScisUser> tem = service.findByRole("教师", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        }
    }

    @PostMapping("/find/student")
    public Map<String, Object> findStudent(@RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String college = jsonObject.getString("college");
        String major = jsonObject.getString("major");
        String name = jsonObject.getString("name");
        String className = jsonObject.getString("className");
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("size");
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        if (college != null && major != null && name != null && className != null) {
            List<Map<String, Object>> mapList = service.findNameAndClassNameAndMajorNameAndCollegeName(
                    name,
                    className,
                    major,
                    college,
                    page,
                    size);
            return getStringObjectMap(res, mapList);
        } else if (college != null && major != null) {
            Page<ScisUser> tem = service.findByScisClassMajorCollegeNameAndScisClassMajorNameAndRole(college, major, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (college != null && name != null) {
            List<Map<String, Object>> mapList = service.findByInfoAndCollege(name, college, "学生", page, size);
            return getStringObjectMap(res, mapList);
        } else if (college != null && className != null) {
            Page<ScisUser> tem = service.findByScisClassMajorCollegeNameAndScisClassNameAndRole(college, className, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (major != null && name != null) {
            List<Map<String, Object>> mapList = service.findByInfoAndMajorName(name, major, page, size);
            return getStringObjectMap(res, mapList);
        } else if (major != null && className != null) {
            Page<ScisUser> tem = service.findByScisClassMajorNameAndScisClassNameAndRole(major, className, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (name != null && className != null) {
            List<Map<String, Object>> mapList = service.findByClassNameAndName(name, className, page, size);
            return getStringObjectMap(res, mapList);
        } else if (college != null) {
            Page<ScisUser> tem = service.findByCollegeNameAndRole(college, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (major != null) {
            Page<ScisUser> tem = service.findByScisClassMajorNameAndRole(major, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else if (name != null) {
            List<Map<String, Object>> mapList = service.findByNameOrLoginOrIdentityAndRole(name, "学生", page, size);
            return getStringObjectMap(res, mapList);
        } else if (className != null) {
            Page<ScisUser> tem = service.findByScisClassNameAndRole(className, "学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        } else {
            Page<ScisUser> tem = service.findByRole("学生", pageable);
            res.put("totalElements", tem.getTotalElements());
            res.put("data", tem.getContent());
            res.put("status", 200);
            res.put("special", 0);
            return res;
        }
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> res, List<Map<String, Object>> mapList) {
        if (mapList != null && !mapList.isEmpty()) {
            res.put("totalElements", mapList.get(0).get("totalElements"));
            res.put("data", mapList);
            res.put("status", 200);
            res.put("special", 1);
            return res;
        }
        res.put("status", 403);
        return res;
    }

    @PostMapping("/resetPwd")
    public Map<String, Object> resetPwd(@RequestBody ScisUser user) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "ok");
        user.setPassword("Ab1234");
        service.save(user);
        return res;
    }

    @PostMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "ok");
        service.deleteById(id);
        return res;
    }

    @PostMapping("/deleteAll")
    public Map<String, Object> deleteAll(@RequestBody List<ScisUser> users) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "ok");
        service.deleteAll(users);
        return res;
    }

    @GetMapping("/text")
    public Map<String, Object> sa() {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        webSocket.sendAllMessage("text");
        return res;
    }

    @PostMapping("/up/data")
    public Map<String, Object> upUserInfo(@RequestParam("file") MultipartFile file,
                                          @RequestParam("id") Integer id,
                                          @RequestParam("role") Integer role) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String fileName = "/" + file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            File file1 = new File(fileName);
            OutputStream outputStream = new FileOutputStream(file1);
            byte[] bytes = new byte[2048];
            int length;
            while ((length = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.close();
            inputStream.close();
            EasyExcel.read(fileName,
                    UserUpObject.class,
                    new ExcelToObject(id, role, webSocket, service, classService, majorService, collegeService)).sheet().doRead();
            file1.delete();
            res.put("status", 200);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", 403);
            return res;
        }
    }

}
