package werls.scis.dao.jpa;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisRole;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 23:20
 */
public interface RoleRepository extends JpaRepository<ScisRole,Integer> {
    /**
     * 身份查询
     * @param name String
     * @return ScisRole
     */
    ScisRole findByName(String name);

}
