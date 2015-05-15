import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Chat_Server_Return implements Runnable
{
    Socket sock;
    private Scanner input;
    private PrintWriter output;
    String message = "";
    public Chat_Server_Return(Socket x)
    {
        this.sock = x;        
    }
    /*
     * checks to see if the client is still on the server, if not a message is sent out telling all other users that the client has disconnected
     */
    public void CheckConnection() throws IOException
    {
        if(!sock.isConnected())
        {
            for(int i = 1; i <= Chat_Server.connectionArray.size(); i++)
            {
                if(Chat_Server.connectionArray.get(i) == sock)
                {
                    Chat_Server.connectionArray.remove(i);
                }
            }
        }
        for(int i = 1; i< Chat_Server.connectionArray.size(); i++)
        {
            Socket temp_sock = Chat_Server.connectionArray.get(i-1);
            PrintWriter temp_out = new PrintWriter(temp_sock.getOutputStream());
            temp_out.println(temp_sock.getLocalAddress().getHostName() + " disconnected. ");
            temp_out.flush();
            System.out.println(temp_sock.getLocalAddress().getHostName() + " disconnected. ");
        }
    }
    /*
     * the heart of this program, runs everything, gets input and output, sends out messages from users
     */
    public void run()
    {
        try
        {
            try
            {
                input = new Scanner(sock.getInputStream());
                output = new PrintWriter(sock.getOutputStream());
                
                while(true)
                {
                    CheckConnection();
                    if(!input.hasNext())
                    {
                        return;
                    }
                    message = input.nextLine();
                    System.out.println("Client said: " + message);
                    for(int i = 1 ; i < Chat_Server.connectionArray.size(); i++)
                    {
                        Socket temp_sock = Chat_Server.connectionArray.get(i-1);
                        PrintWriter temp_out = new PrintWriter(temp_sock.getOutputStream());
                        temp_out.println(message);
                        temp_out.flush();
                        System.out.println("Sent to: " + temp_sock.getLocalAddress().getHostName());
                    }
                }
            }
            finally
            {
                sock.close();
            }
        }
        catch(Exception x)
        {
            System.out.println(x);
        }
    }
}
                