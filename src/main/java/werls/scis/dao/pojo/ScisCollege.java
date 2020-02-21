package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 学院
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:00
 */
@Entity
@Table(name = "Is_college")
public class ScisCollege implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    private long id;
    @Column(name = "college_name")
    private String collegeName;
    /**
     *开设的专业
     */
    @OneToMany(mappedBy = "college",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JoinColumn(name = "college_id",referencedColumnName = "college_id")
    private List<ScisMajor> majors;
    /**
     * 学院教师
     */
    @OneToMany(mappedBy = "college")
    private List<ScisUser> scisUserList;

    @Override
    public String toString() {
        return "ScisCollege{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", majors=" + majors +
                ", userList=" + scisUserList +
                '}';
    }

    public List<ScisUser> getScisUserList() {
        return scisUserList;
    }

    public void setScisUserList(List<ScisUser> scisUserList) {
        this.scisUserList = scisUserList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public List<ScisMajor> getMajors() {
        return majors;
    }

    public void setMajors(List<ScisMajor> majors) {
        this.majors = majors;
    }
}
