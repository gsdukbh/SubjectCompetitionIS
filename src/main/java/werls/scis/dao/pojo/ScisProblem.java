package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 问题反馈
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:08
 */
@Entity
@Table(name = "Is_problem")
public class ScisProblem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="problem_id")
    private long id;
    @Column(name = "problem_time")
    private Date time;
    @Column(name = "problem_type")
    private String type;
    @Column(name = "problem_title")
    private String title;
    @Column(name = "problem_content")
    private String content;
    /**
     * 用户反馈问题
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Is_user_problem",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "problem_id")})
    private List<ScisUser> scisUserList;

    /**
     * 竞赛问题
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Is_competition_problem",
            joinColumns = {@JoinColumn(name = "competition_id")},
            inverseJoinColumns = {@JoinColumn(name = "problem_id")})
    private List<ScisCompetition> competitionList;

    /**
     * 作品问题
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Is_works_problem",
            joinColumns = {@JoinColumn(name = "works_id")},
            inverseJoinColumns = {@JoinColumn(name = "problem_id")})
    private List<ScisWorks> worksList;

    @Override
    public String toString() {
        return "ScisProblem{" +
                "id=" + id +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userList=" + scisUserList +
                ", competitionList=" + competitionList +
                ", worksList=" + worksList +
                '}';
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ScisCompetition> getCompetitionList() {
        return competitionList;
    }

    public void setCompetitionList(List<ScisCompetition> competitionList) {
        this.competitionList = competitionList;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScisUser> getScisUserList() {
        return scisUserList;
    }

    public void setScisUserList(List<ScisUser> scisUserList) {
        this.scisUserList = scisUserList;
    }

    public List<ScisWorks> getWorksList() {
        return worksList;
    }

    public void setWorksList(List<ScisWorks> worksList) {
        this.worksList = worksList;
    }
}
