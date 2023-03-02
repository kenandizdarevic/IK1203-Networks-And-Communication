import java.net.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;
import java.io.*;

public class MyRunnable implements Runnable {
    private Socket clientSocket;
    public MyRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try{
            byte[] buffer = new byte[1024];
            String hostname = null;
            Integer port = 0;
            boolean shutdown = false;
            Integer timeout = null;
            Integer limit = null;
            String stringToSend = "";

            //Socket clientSocket = serverSocket.accept();
            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();

            int data = inputStream.read(buffer);
            String request = new String(buffer, 0, data); // convert request to string
            
            String[] components = request.split("[ ?]"); // split request by space and ?
            String method = components[0]; // HTTP method, GET
            String path = components[1]; // path component of URI
            // String version = components[2]; // HTTP version, e.g. HTTP/1.1
            String[] parameters = components[2].split("&"); // split params to string array

            System.out.println("Request recieved! ");
            System.out.println("Method: " + method);
            System.out.println("Path: " + path);
        // System.out.println("Version: " + version);
            
            if (!method.equals("GET")) {
                String response = "HTTP/1.1 400 Bad Request\r\n\r\n";
                outputStream.write(response.getBytes());
            }

            if (!path.startsWith("/ask")) {
                String response = "HTTP/1.1 404 Not Found\r\n\r\n";
                outputStream.write(response.getBytes());
                return;
            }

            for (String params : parameters) {
                String[] paramPart = params.split("=");
                String paramKey = paramPart[0];
                String paramValue = paramPart[1];

                switch (paramKey) {
                    case "hostname":
                        hostname = paramValue;
                        break;
                    case "port":
                        port = Integer.parseInt(paramValue);
                        break;
                    case "string":
                        stringToSend = paramValue;
                        break;
                    case "timeout":
                        timeout = Integer.parseInt(paramValue);
                        break;
                    case "limit":
                        limit = Integer.parseInt(paramValue);
                        break;
                    case "shutdown":
                        shutdown = Boolean.parseBoolean(paramValue);
                        break;
                }
            }
            System.out.println("Hostname: " + hostname);
            System.out.println("Port: " + port);
            System.out.println("To send: " + stringToSend);
            System.out.println("Timeout: " + timeout);
            System.out.println("Limit: " + limit);
            System.out.println("Shutdown: " + shutdown);

            TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
            byte[] ts = stringToSend.getBytes();
            byte[] fromServerBytes = tcpClient.askServer(hostname, port, ts);
            String fromServerString = new String(fromServerBytes, StandardCharsets.UTF_8);

            String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length:" + fromServerString.length() + "\r\n" +
                            "\r\n" +
                            fromServerString;
            byte[] responseBytes = response.getBytes("UTF-8");
            System.out.println(fromServerString);
            outputStream.write(responseBytes);
            outputStream.flush();
        } catch(IOException exception) {
            System.out.println("Something went wrong: " + exception);
        }
    }
}