import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientEntity extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;


    public ClientEntity(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        start();
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            String read;
            while ((read = reader.readLine()) != null) {
                if (read.equals("exit")) {
                    break;
                }
                final String data = read;
                System.out.println("---> received: " + read);
                //вызов метода отправки сообщения всем клиентам
                // - желательно в отдельном потоке
                new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < server.getClients().size(); i++) {
                            server.getClients().get(i).writer.println(data);
                            server.getClients().get(i).writer.flush();
                        }
                    }
                }).start();
            }
            writer.close();
            server.remove(this);
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}