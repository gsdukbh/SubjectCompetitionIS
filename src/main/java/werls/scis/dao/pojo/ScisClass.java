package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 班级
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 21:58
 */
@Entity
@Table(name = "Is_class")
public class ScisClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="class_id")
    private long id;
    @Column(name = "class_grade")
    private String grade;
    @Column(name = "class_name")
    private String name;
    /**
     * 班级专业
     */
    @ManyToOne(fetch =FetchType.LAZY,optional = false)
    @JoinColumn(name = "major_id",referencedColumnName = "major_id")
    private ScisMajor major;

    @OneToMany(mappedBy = "scisClass",fetch = FetchType.LAZY)
    private  List<User> userList;

    @Override
    public String toString() {
        return "ScisClass{" +
                "id=" + id +
                ", grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                ", major=" + major +
                ", userList=" + userList +
                '}';
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScisMajor getMajor() {
        return major;
    }

    public void setMajor(ScisMajor major) {
        this.major = major;
    }
}
