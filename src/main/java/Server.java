import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 60123;
    private ServerSocket serverSocket;
    private volatile ArrayList<ClientEntity> clients;

    public ArrayList<ClientEntity> getClients() {
        return clients;
    }

    public void start() {
        clients = new ArrayList<ClientEntity>();
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();

                clients.add(new ClientEntity(client, this));
                System.out.println("=== client connected ===");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(ClientEntity client) {
        clients.remove(client);
        System.out.println("=== client disconnected ===");
    }

    public ArrayList<User> createFakeUsers(){
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("Kolya", "12345"));
        users.add(new User("Vasya", "56789"));
        users.add(new User("Lena", "324553"));
        users.add(new User("Sveta", "234588"));
        return users;
    }
}
