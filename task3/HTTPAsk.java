import java.net.*;
import java.io.*;

public class HTTPAsk {
    public static void main(String[] args) throws IOException{
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            while(true) {
                // System.out.println("Testing");
                Socket connectionSocket = serverSocket.accept();
                // Read request from connectionSocekt
                InputStream input = connectionSocket.getInputStream();
                // Write reply to connectionSocket
                OutputStream output = connectionSocket.getOutputStream();
                // Close connectionSocket
                System.out.println(output);
            }
        } catch(IOException exception) {
            System.err.println(exception);
        }
    }
}

