import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Chat_Client implements Runnable
{
    Socket sock;
    Scanner in;
    Scanner send = new Scanner(System.in);
    PrintWriter out;
    
    public Chat_Client(Socket x)
    {
        this.sock = x;
    }
    /*
     * creates the client socket, input output for client(user), connects and disconnects from the server
     */
    public void run()
    {
        try
        {
            try
            {
                in = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());
                out.flush();
                checkStream();
            }
            finally
            {
                sock.close();
            }
        }
        catch(Exception x)
        {
            System.out.print(x);
        }
    }
    /*
     * disconnects client from the server
     */
    public void disconnect() throws IOException
    {
        out.println(Chat_Client_GUI.userName + " has disconnected");
        out.flush();
        sock.close();
        JOptionPane.showMessageDialog(null, "You Disconnected, you dummy.");
    }
    /*
     * checks input stream
     */
    public void checkStream()
    {
        while(true)
        {
            recieve();
        }
    }
    /*
     * recieves input from a user
     */
    public void recieve()
    {
        if(in.hasNext())
        {
            String message = in.nextLine();
            if(message.contains("###"))
            {
                String t1 = message.substring(3);
                t1 = t1.replace("[","");
                t1 = t1.replace("]","");
            
                String[] currentUsers = t1.split(", ");
                Chat_Client_GUI.jlOnline.setListData(currentUsers);
            }
            
            else
            {
                Chat_Client_GUI.taConversation.append(message + "\n");
            }
    }
    }
    /*
     * sends the message from the input stream
     */
    public void send(String x)
    {
        out.println(Chat_Client_GUI.userName + ": " + x);
        out.flush();
        Chat_Client_GUI.tfMessage.setText("");
    }
}
                
                