package werls.scis.controller.admin;

import com.alibaba.excel.EasyExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.UserServiceImpl;
import werls.scis.util.ExcelToObject;
import werls.scis.util.UserUpObject;
import werls.scis.webSocket.WebSocket;

import java.io.*;
import java.util.List;
import java.util.Map;
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
    UserServiceImpl userService;
    @Autowired
    WebSocket webSocket;

    @PostMapping("/delete/{id}")
    public  Map<String, Object> delete(@PathVariable Integer id){
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "ok");
        userService.deleteById(id);
        return res;
    }

    @PostMapping("/deleteAll")
    public Map<String, Object> deleteAll(@RequestBody List<ScisUser> users) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "ok");
        userService.deleteAll(users);
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
    public Map<String, Object> upUserInfo(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
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
            EasyExcel.read(fileName, UserUpObject.class, new ExcelToObject(id, webSocket)).sheet().doRead();
            file1.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

}
