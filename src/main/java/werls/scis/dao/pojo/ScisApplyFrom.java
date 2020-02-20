package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 竞赛报名
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:01
 */
@Entity
@Table(name = "Is_apply_from")
public class ScisApplyFrom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="competition_id")
    private long id;


}
