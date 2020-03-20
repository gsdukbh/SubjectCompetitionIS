package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * 公告
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:12
 */
@Entity
@Table(name = "Is_announcement")
public class ScisAnnouncement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="announcement_id")
    private Integer id;
    /**
     * 标题
     */
    @Column(name = "announcement_title")
    private String title;
    /**
     * 内容
     */
    @Column(name = "announcement_content")
    private String content;
    /**
     * 公告时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "announcement_time")
    private Date time;
    /**
     * 作者
     */
    @Column(name = "announcement_author")
    private String author;
    /**
     * 来源，一般是学院
     */
    @Column(name = "announcement_from")
    private String from;
    /**
     * 状态
     */
    @Column(name = "announcement_status")
    private String status;
    /**
     * 类型，主要分开考试信息和竞赛信息
     */
    @Column(name = "announcement_type")
    private String type;
    /**
     * 用户多对一
     */

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"announcements"})
    private ScisUser scisUser;

    
    @Override
    public String toString() {
        return "ScisAnnouncement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", author='" + author + '\'' +
                ", from='" + from + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ScisUser getScisUser() {
        return scisUser;
    }

    public void setScisUser(ScisUser scisUser) {
        this.scisUser = scisUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
