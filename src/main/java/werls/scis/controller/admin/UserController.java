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
import werls.scis.dao.pojo.ScisClass;
import werls.scis.dao.pojo.ScisCollege;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.*;
import werls.scis.util.ExcelToObject;
import werls.scis.util.Tools;
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
    UserService service;
    @Autowired
    ClassServiceImpl classService;
    @Autowired
    MajorServiceImpl majorService;
    @Autowired
    CollegeServiceImpl collegeService;
    @Autowired
    WebSocket webSocket;
    @Autowired
    Tools tools;

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        UserUpObject userUpObject = JSONObject.toJavaObject(jsonObject, UserUpObject.class);
        ScisUser user = new ScisUser();
        user.setRole(userUpObject.getRole());
        ScisRole role = new ScisRole();
        List<ScisRole> roleList = new ArrayList<>();

        user.setPassword(userUpObject.getPassword());
        user.setStatus(userUpObject.getStatus());
        user.setLogin(userUpObject.getLogin());
        user.setName(userUpObject.getName());
        user.setSex(userUpObject.getSex());
        user.setEmail(userUpObject.getEmail());
        user.setIdentity(userUpObject.getIdentity());
        user.setPhone(userUpObject.getPhone());

        Optional<ScisClass> scisClass = classService.findByName(userUpObject.getClassName());
        Optional<ScisCollege> scisCollege = collegeService.findByCollegeName(userUpObject.getCollege());

        if ("学生".equals(user.getRole())) {
            role.setId(2);
            roleList.add(role);
            user.setRoles(roleList);
            if (scisClass.isPresent()) {
                tools.isClass(user, scisClass.get(), userUpObject);
            } else {
                tools.noClass(user, userUpObject);
            }
        } else if ("教师".equals(user.getRole())) {
            role.setId(3);
            roleList.add(role);
            user.setRoles(roleList);
            ScisCollege temCollege = scisCollege.map(tools::isCollege).orElseGet(() -> tools.noCollege(userUpObject));
            user.setCollege(temCollege);
        } else if ("管理员".equals(user.getRole())) {
            role.setId(1);
            roleList.add(role);
            user.setRoles(roleList);
        } else {
            res.put("status", 403);
            return res;
        }
        res.put("status", 200);
        service.save(user);
        return res;
    }

    @GetMapping("/findById/{id}")
    public Map<String, Object> findById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisUser> user = service.findById(id);
        if (user.isPresent()) {
            res.put("status", 200);
            res.put("data", user.get());
        } else {
            res.put("status", 404);
        }
        return res;
    }

    @PostMapping("/upData/{roleId}")
    public Map<String, Object> saveUser(@PathVariable Integer roleId, @RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisUser user = new ScisUser();
        UserUpObject userUpObject = JSONObject.toJavaObject(jsonObject, UserUpObject.class);
        Optional<ScisUser> tem = service.findById(userUpObject.getId());
        if (tem.isPresent()) {
            user.setId(userUpObject.getId());
            user.setRole(tem.get().getRole());
            user.setPassword(tem.get().getPassword());
            user.setStatus(tem.get().getStatus());
            user.setLogin(tem.get().getLogin());

            user.setName(userUpObject.getName());
            user.setSex(userUpObject.getSex());
            user.setEmail(userUpObject.getEmail());
            user.setIdentity(userUpObject.getIdentity());
            user.setPhone(userUpObject.getPhone());
            Optional<ScisClass> scisClass = classService.findByName(userUpObject.getClassName());
            Optional<ScisCollege> scisCollege = collegeService.findByCollegeName(userUpObject.getCollege());

            if (roleId == 2) {
                if (scisClass.isPresent()) {
                    tools.isClass(user, scisClass.get(), userUpObject);
                } else {
                    tools.noClass(user, userUpObject);
                }
            } else {
                ScisCollege temCollege = scisCollege.map(tools::isCollege).orElseGet(() -> tools.noCollege(userUpObject));
                user.setCollege(temCollege);
            }
            res.put("status", 200);
            res.put("data", service.upData(user));

        } else {
            res.put("status", 403);
        }
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
        Optional<ScisUser> tem = service.findById(user.getId());
        if (tem.isPresent()) {
            List<ScisRole> list = tem.get().getRoles();
            res.put("data", list);
            res.put("status", 200);
        } else {
            res.put("status", 403);
        }
        return res;
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
        try {
            for (ScisUser user : users) {
                service.deleteById(user.getId());
                res.put("status", 200);
                res.put("message", "ok");
            }
        } catch (Exception e) {
            res.put("status", 403);
            e.printStackTrace();
        }

        return res;
    }

    @GetMapping("/text")
    public Map<String, Object> sa() {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("data", service.findByLogin("root") != null);
        return res;
    }

    @PostMapping("/up/data")
    public Map<String, Object> upUserInfo(@RequestParam("file") MultipartFile file,
                                          @RequestParam("id") Integer id,
                                          @RequestParam("role") Integer role) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String fileName = "/temp/" + file.getOriginalFilename();
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
                    new ExcelToObject(
                            id,
                            role,
                            webSocket,
                            tools
                    ))
                    .sheet().doRead();
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
