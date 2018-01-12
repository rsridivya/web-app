package pkg.gannet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleServer {

/**
 * @param args
 */
public static void main(String[] args) throws IOException{

    while(true){
        ServerSocket serverSock = new ServerSocket(8000);
        Socket sock = serverSock.accept();

        System.out.println("connected");

        InputStream sis = sock.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(sis));
        String request = br.readLine(); // Now you get GET index.html HTTP/1.1`
        String[] requestParam = request.split(" ");
        String path = requestParam[1];

        System.out.println(path);

        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader bfr = null;
        String s = "Hi";

        if (path.length()>0) {
            System.out.println("writing not found...");
             out.write("HTTP/1.0 200 OK\r\n");
             out.write(new Date() + "\r\n");
             out.write("Content-Type: text/html");
             out.write("Content length: " + s.length() + "\r\n");
             out.write(s);
        }else{
                out.write("String Length is 0");
        }
        if(bfr != null){
            bfr.close();
        }
        br.close();
        out.close();
        serverSock.close();
    }
}

}
