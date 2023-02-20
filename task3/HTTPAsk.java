import java.net.*;
import java.nio.charset.StandardCharsets;

import tcpclient.TCPClient;
import java.io.*;

public class HTTPAsk {
    private static int BUFFERSIZE = 1024;

    public static void main(String[] args) throws Exception, IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        try {
            while(true) {
                byte[] buffer = new byte[BUFFERSIZE]; 

                Integer port = 0;
                String hostname = null;
                boolean shutdown = false;
                Integer timeout = null;
                Integer limit = null; 
                String string = null;
                byte[] toServerBytes = null;

                Socket connectionSocket = serverSocket.accept();
                InputStream input = connectionSocket.getInputStream();
                OutputStream output = connectionSocket.getOutputStream();
                
                String request = new String(buffer, 0, input.read(buffer));
                URL url = new URL("http://" + request.split("\r\n")[1].split(" ")[1]);
                String host = url.getHost();
                String query = url.getQuery();
                String path = url.getPath();
                String[] parameters = query.split("&");

                for (String params : parameters) {
                    String[] paramPart = params.split("=");
                    String paramName = paramPart[0];
                    String paramValue = paramPart[1];

                    switch(paramName) {
                        case "hostname":
                            hostname = paramValue;
                            break;
                        case "port":
                            port = Integer.parseInt(paramValue);
                            break;
                        case "shutdown":
                            shutdown = Boolean.parseBoolean(paramValue);
                            break;
                        case "timeout":
                            timeout = Integer.parseInt(paramValue);
                            break;
                        case "limit":
                            limit = Integer.parseInt(paramValue);
                            break;
                        case "string":
                            toServerBytes = paramValue.getBytes();
                            break;
                    }
                }
            
                TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
                byte[] result = tcpClient.askServer(hostname, port, buffer);
                String response = new String(result, StandardCharsets.UTF_8);

                output.write(("HTTP/1.1 200 OK \r\n \r\n" + response).getBytes());
            } 
        } catch(IOException exception) {
            System.err.println(exception);
            serverSocket.close();
        }
    }
}

