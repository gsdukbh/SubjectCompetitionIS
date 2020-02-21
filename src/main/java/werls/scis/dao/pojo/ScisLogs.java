package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * 日志
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:00
 */
@Entity
@Table(name = "Is_logs")
public class ScisLogs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logs_id")
    private long id;

    @Column(name = "logs_content")
    private String content;
    @Column(name = "logs_time")
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @Override
    public String toString() {
        return "ScisLogs{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", user=" + user +
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
