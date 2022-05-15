import java.io.Serializable;

public class UserTime implements Serializable {
    public String username;
    public long time;

    UserTime(String username,long time){
        this.username=username;
        this.time=time;
    }
}
