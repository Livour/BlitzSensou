import java.io.Serializable;
import java.util.ArrayList;

public class Leaderboard implements Serializable {
    ArrayList<UserScore> topScore;
    ArrayList<UserTime> topTime;

    public Leaderboard(ArrayList<UserScore> topScore,ArrayList<UserTime> topTime){
        this.topScore=topScore;
        this.topTime=topTime;
    }
}
