import java.net.*;
import java.io.*;

public class ConcHTTPAsk {

    public static void main(String[] args) throws IOException, Exception {
        try {    
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            System.out.println("Server running on port: " + Integer.parseInt(args[0]));
            while (true) {
                Socket clienSocket = serverSocket.accept();
                Thread thread = new Thread(new MyRunnable(clienSocket));
                thread.start();
            }
        } catch(IOException exception) {
            throw new IOException("Something went wrong: " + exception);
        }
    }
}
