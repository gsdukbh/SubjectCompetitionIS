package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.RoleRepository;
import werls.scis.dao.pojo.ScisRole;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServicImpl {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * 身份查询
     *
     * @param name String
     * @return ScisRole
     */
    public ScisRole findByName(String name) {
        return roleRepository.findByName(name);
    }

    public void save(ScisRole role) {
        this.roleRepository.save(role);
    }

    public void saveAll(List<ScisRole> role) {
        roleRepository.saveAll(role);
    }
    public Page<ScisRole> findAll (Pageable pageable){
        return roleRepository.findAll(pageable);
    }

}
