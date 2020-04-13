package werls.scis.dao.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年04月13日 11:40
 */

public class ScisTeamUserApply {

    private ScisUser user;
    private boolean isCaptain;

    private boolean isApply;

    private boolean isRead;


    public ScisUser getUser() {
        return user;
    }

    public void setUser(ScisUser user) {
        this.user = user;
    }


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }


    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }

    public boolean isApply() {
        return isApply;
    }

    public void setApply(boolean apply) {
        isApply = apply;
    }
}
