package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 专业
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:01
 */
@Entity
@Table(name = "Is_major")
public class ScisMajor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private long id;
    @Column(name = "major_name")
    private String majorName;
    @Column(name = "education_level")
    private String majorLevel;

    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "college_id")
    private ScisCollege college;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id",referencedColumnName = "major_id")
    private List<ScisClass> classList;

    @Override
    public String toString() {
        return "ScisMajor{" +
                "id=" + id +
                ", majorName='" + majorName + '\'' +
                ", majorLevel='" + majorLevel + '\'' +
                ", college=" + college +
                ", classList=" + classList +
                '}';
    }

    public List<ScisClass> getClassList() {
        return classList;
    }

    public void setClassList(List<ScisClass> classList) {
        this.classList = classList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorLevel() {
        return majorLevel;
    }

    public void setMajorLevel(String majorLevel) {
        this.majorLevel = majorLevel;
    }

    public ScisCollege getCollege() {
        return college;
    }

    public void setCollege(ScisCollege college) {
        this.college = college;
    }
}
