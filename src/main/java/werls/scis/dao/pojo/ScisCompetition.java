package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 竞赛
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:13
 */
@Entity
@Table(name = "Is_competition")
public class ScisCompetition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="competition_id")
    private long id;
    @Column(name = "competition_name")
    private String name;
    @Column(name = "competition_status")
    private String status;
    @Column(name = "competition_start_time")
    private Date startTime;
    @Column(name = "competition_end_time")
    private Date endTime;
    @Column(name = "competition_content")
    private String content;
    @Column(name = "competition_url")
    private String url;
    @Column(name = "competition_author")
    private String author;
    @Column(name = "competition_level")
    private String level;
    @Column(name = "competition_organizer")
    private String organizer;
    @Column(name = "competition_num_limit")
    private String numLimit;

    /**
     * 个人报名
     */
    @OneToMany(mappedBy = "competition",fetch = FetchType.LAZY)
    private List<ScisApplyFrom> applyFromList;
    /**
     * 团体报名
     */
    @OneToMany(mappedBy = "competition",fetch = FetchType.LAZY)
    private List<ScisTeamApply> teamApplyList;
    /**
     * 竞赛作品
     */
    @OneToMany(mappedBy = "competition")
    private List<ScisWorks> works;

    @Override
    public String toString() {
        return "ScisCompetition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", level='" + level + '\'' +
                ", organizer='" + organizer + '\'' +
                ", numLimit='" + numLimit + '\'' +
                ", applyFromList=" + applyFromList +
                ", teamApplyList=" + teamApplyList +
                ", works=" + works +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getNumLimit() {
        return numLimit;
    }

    public void setNumLimit(String numLimit) {
        this.numLimit = numLimit;
    }

    public List<ScisApplyFrom> getApplyFromList() {
        return applyFromList;
    }

    public void setApplyFromList(List<ScisApplyFrom> applyFromList) {
        this.applyFromList = applyFromList;
    }
}
