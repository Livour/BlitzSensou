import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class GameSocket {
    //config
    private final String GETLEADERBOARD = "get_leaderboard";
    private final String GETUSER = "get_user";
    private final String SET = "set";
    private final String IP = "127.0.0.1";
    private final int PORT = 6666;

    private Socket clientSocket;
    private PrintWriter out;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private BufferedReader in;
    public boolean status;

    public void startConnection() throws IOException {
        status=true;
        try {
            clientSocket = new Socket(IP, PORT);
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        if (clientSocket == null) {
            return;
        }
        clientSocket.setKeepAlive(true);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public boolean setData(User u) throws IOException {
        out.println(SET);
        objectOutputStream.writeObject(u);
        boolean isBest = in.readLine().equals("true") ? true : false;
        return isBest;
    }

    public User getUser(String username) throws IOException {
        User u;
        out.println(GETUSER);
        out.println(username);
        try {
            u = (User) objectInputStream.readObject();
            return u;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Leaderboard getLeaderboard() throws IOException, ClassNotFoundException {
        Leaderboard lb;
        out.println(GETLEADERBOARD);
        lb = (Leaderboard) objectInputStream.readObject();
        return lb;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        objectOutputStream.close();
    }
}