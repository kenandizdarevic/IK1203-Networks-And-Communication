import java.net.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;
import java.io.*;

public class HTTPAsk {
    private static int BUFFERSIZE = 1024;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        System.out.println("Server running on port: " + Integer.parseInt(args[0]));
        while (true) {
            Socket clientSocket = serverSocket.accept();
            OutputStream outputStream = clientSocket.getOutputStream();

            String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: 12\r\n" +
                            "\r\n" +
                            "Hello World!";
            byte[] resonseBytes = response.getBytes("UTF-8");

            outputStream.write(resonseBytes);
            outputStream.flush();
        }
    
    }

  /*  public static void main(String[] args) throws Exception, IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        try {
            while(true) {
                byte[] buffer = new byte[BUFFERSIZE]; 

                System.out.println("Waiting for request!");
                Integer port = 0;
                String hostname = null;
                boolean shutdown = false;
                Integer timeout = null;
                Integer limit = null; 
                String string = "";
                byte[] toServerBytes = new byte[0];

                Socket connectionSocket = serverSocket.accept();
                System.out.println("Connection");
                InputStream input = connectionSocket.getInputStream();
                OutputStream output = connectionSocket.getOutputStream();
                int data = input.read(buffer);
                
                String request = new String(buffer, 0, data);
                String httpMethod = request.split("\r\n")[0].split(" ")[0];
                String httpVersion = request.split("\r\n")[0].split(" ")[2];
                String path = request.split("\r\n")[0].split(" ")[1];
                               
                if (!httpMethod.contains("GET") || !httpVersion.contains("HTTP/1.1")) {
                    String response = "HTTP/1.1 400 Bad Request\r\n\r\n";
                    output.write(response.getBytes());
                    throw new Exception(response);
                } else if(!path.startsWith("/ask")) {
                    String response = "HTTP/1.1 404 Not Found\r\n\r\n";
                    output.write(response.getBytes());
                    throw new Exception(response);
                }

                String query = path.split("\\?")[1];
                String[] parameters = query.split("&");

                for (String params : parameters) {
                   // System.out.println("HERE");
                    String[] paramPart = params.split("=");
                    String paramName = paramPart[0];
                    String paramValue = paramPart[1];

                    switch(paramName) {
                        case "hostname":
                            hostname = paramValue;
                            System.out.println(hostname);
                            break;
                        case "port":
                            port = Integer.parseInt(paramValue);
                            System.out.println(port);
                            break;
                        case "shutdown":
                            shutdown = Boolean.parseBoolean(paramValue);
                            System.out.println(shutdown);
                            break;
                        case "timeout":
                            timeout = Integer.parseInt(paramValue);
                            System.out.println(timeout);
                            break;
                        case "limit":
                            limit = Integer.parseInt(paramValue);
                            System.out.println(limit);
                            break;
                        case "string":
                            string = paramValue + "\n";
                            break;
                    }
                }   
                toServerBytes = string.getBytes();
                TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
                System.out.println("TCP Client initalized");
                byte[] fromServerBytes = tcpClient.askServer(hostname, port, toServerBytes);
                System.out.println("Ask server");
                String response1 = new String(fromServerBytes, StandardCharsets.UTF_8);
                String response = "HTTP/1.1 200 OK\r\n" + response1;
                System.out.println(response);
                output.write(response.getBytes());                
            }

        } catch(IOException exception) {
            System.err.println(exception);
            serverSocket.close();
        } finally {
            serverSocket.close();
        }
    } */
}

