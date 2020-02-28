package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.MajorRepostitory;
import werls.scis.dao.jpa.MenuRepository;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisMenu;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl {
    @Autowired
    private MenuRepository menuRepository;

    public void save (ScisMenu menu){
        menuRepository.save(menu);
    }
    public void saveAll (List<ScisMenu> menu){
        menuRepository.saveAll(menu);
    }
    public Page<ScisMenu> findAll(Pageable pageable){
        return this.menuRepository.findAll(pageable);
    }

    public void delete(ScisMenu menu){
        this.menuRepository.delete(menu);
    }
    public void deleteAll(List<ScisMenu> menu){
        this.menuRepository.deleteAll(menu);
    }
    public void deleteById(Integer id){
        this.menuRepository.deleteById(id);
    }

}
