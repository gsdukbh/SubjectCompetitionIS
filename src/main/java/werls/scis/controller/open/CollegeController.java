package werls.scis.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisCollege;
import werls.scis.service.CollegeServiceImpl;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月08日 10:49
 */
@RestController
@RequestMapping("/public/college")
public class CollegeController {
    @Autowired
    CollegeServiceImpl service;

    @GetMapping("/findAll")
    @Cacheable(value = "college",unless = "#result == null ",key = "'college'")
    public Page<ScisCollege> findAll(){
            Pageable pageable1= PageRequest.of(0, 20, Sort.by("name"));
            return  service.findAll(pageable1);
    }

}
