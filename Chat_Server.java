import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
//import javax.util.swing.JOptionPane;

public class Chat_Server
{
    public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
    public static ArrayList<String> currentUsers = new ArrayList<String>();
    
    /*
     * the main method that creates the server socket
     */
    public static void main(String[] args) throws IOException
    {
        try 
        {
            final int PORT = 6001;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Waiting for Client...");
            
            while(true)
            {
                Socket sock = SERVER.accept();
                connectionArray.add(sock);
                
                System.out.println("Client connection from: " + sock.getLocalAddress().getHostName());
                
                AddUserName(sock);
                
                Chat_Server_Return chat = new Chat_Server_Return(sock);
                Thread x = new Thread(chat);
                x.start();
            }
        }
        catch(Exception x)
        {
            System.out.print(x);
        }
    }
    /*
     * Adds the username to the client list
     */
    
    public static void AddUserName(Socket x) throws IOException
    {
        Scanner in = new Scanner(x.getInputStream());
        String userName = in.nextLine();
        currentUsers.add(userName);
        
        for(int i = 1; i <= Chat_Server.connectionArray.size(); i++)
        {
            Socket temp_sock =  Chat_Server.connectionArray.get(i-1);
            PrintWriter out = new PrintWriter(temp_sock.getOutputStream());
            out.println("####" + currentUsers);
            out.flush();
        }
    }
}