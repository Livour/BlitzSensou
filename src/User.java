import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public int score;
    public long time;

    User(String username,int score,long time){
        this.username=username;
        this.score=score;
        this.time=time;
    }
}
