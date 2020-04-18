package werls.scis.controller.open;


import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.UserService;
import werls.scis.service.WorksServiceImpl;
import werls.scis.util.ExcelToScore;
import werls.scis.util.Score;
import werls.scis.util.Tools;
import werls.scis.webSocket.WebSocket;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.open
 * @Description: TODO
 * @date Date : 2020年04月19日 15:19
 */
@RestController
@RequestMapping("/public/score")
public class ScoreController {

    @Autowired
    ApplyFromSericeImpl applyFromSerice;

    @Autowired
    WebSocket webSocket;
    @Autowired
    Tools tools;

    @PostMapping("/findScore")
    public Map<String, Object> findScore(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "100") Integer size,
                                         @RequestParam(name = "userName", defaultValue = "") String userName,
                                         @RequestParam(name = "competitionId", defaultValue = "") Integer competitionId) {
        Map<String, Object> res = new HashMap<>(16);
        res.put("status", 200);
        res.put("content", applyFromSerice.findGrades(competitionId, userName, page, size));
        res.put("totalElements", applyFromSerice.findGrades(userName, competitionId).get("totalElements"));
        return res;
    }


}
