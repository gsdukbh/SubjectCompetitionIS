package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:02
 */
@Entity
@Table(name = "Is_rule")
public class ScisRule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private long id;
    @Column(name = "rule_authority")
    private String authority;

    @Override
    public String toString() {
        return "ScisRule{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
