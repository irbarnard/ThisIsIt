//gui done by cgermany77//

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Chat_Client_GUI
{
    private static Chat_Client chatClient;
    public static String userName = "Anonymous";

    public static JFrame mainWindow = new JFrame();
    public static JFrame loginWindow = new JFrame();

    public static JTextField tfMessage = new JTextField(20);
    public static JTextField tfUserNameBox = new JTextField(20);

    public static JTextArea taConversation = new JTextArea();

    public static JList jlOnline = new JList();

    private static JButton bAbout = new JButton();
    private static JButton bConnect = new JButton(); 
    private static JButton bDisconnect = new JButton();
    private static JButton bHelp = new JButton();
    private static JButton bSend = new JButton();
    private static JButton bEnter = new JButton("ENTER");

    private static JLabel lMessage = new JLabel("Message: ");
    private static JLabel lConversation = new JLabel();
    private static JLabel lOnline = new JLabel();
    private static JLabel lLoggedInAs = new JLabel();
    private static JLabel lLoggedInAsBox = new JLabel();
    private static JLabel lEnterUserName = new JLabel("Enter username: ");

    private static JScrollPane spConversation = new JScrollPane();
    private static JScrollPane spOnline = new JScrollPane();

    private static JPanel pLogIn = new JPanel();

    public static void main(String[] args)
    {
        buildMainWindow();
        initialize();
    }

    /*
     * creates client gui socket and connects to the server
     */
    public static void connect()
    {
        try
        {
            final int port = 6001;
            final String host = "127.0.0.1";
            Socket sock = new Socket(host, port);
            System.out.println("You connected to: " + host);

            chatClient = new Chat_Client(sock);

            PrintWriter out = new PrintWriter(sock.getOutputStream());
            out.println(userName);
            out.flush();

            Thread x = new Thread(chatClient);
        }
        catch(Exception x)
        {
            System.out.println(x);
            JOptionPane.showMessageDialog(null, "Server not responding...");
            System.exit(0);
        }
    }

    public static void initialize()
    {
        bSend.setEnabled(true);
        bDisconnect.setEnabled(true);
        bConnect.setEnabled(true);
    }
    /*
     * builds the log in window
     */
    public static void buildLogInWindow()
    {
        loginWindow.setTitle("Whats your name?");
        loginWindow.setSize(400, 100);
        loginWindow.setLocation(250,200);
        loginWindow.setResizable(false);
        pLogIn = new JPanel();
        pLogIn.add(lEnterUserName);
        pLogIn.add(tfUserNameBox);
        pLogIn.add(bEnter);
        loginWindow.add(pLogIn);

        logInAction();
        loginWindow.setVisible(true);
    }
    /*
     * builds the main window
     */
    public static void buildMainWindow()
    {
        mainWindow.setTitle(userName + "'s Chat Box");
        mainWindow.setSize(450, 500);
        mainWindow.setLocation(220,180);
        mainWindow.setResizable(false);
        configureMainWindow();
        mainWindowAction();
        mainWindow.setVisible(true);
    }
    /*
     * creates all of the buttons/ frames/ boxes/ etc
     */
    public static void configureMainWindow()
    {
        mainWindow.setBackground(new java.awt.Color(255, 255, 255));
        mainWindow.setSize(500, 320);
        mainWindow.getContentPane().setLayout(null);

        bSend.setBackground(new java.awt.Color(0,0,255));
        bSend.setBackground(new java.awt.Color(0, 0, 255));             
        bSend.setForeground(new java.awt.Color(255, 255, 255));
        bSend.setText("SEND");
        mainWindow.getContentPane().add(bSend);
        bSend.setBounds(250, 40, 81, 25);

        bDisconnect.setBackground(new java.awt.Color(0, 0, 255));
        bDisconnect.setForeground(new java.awt.Color(255, 255, 255));
        bDisconnect.setText("DISCONNECT");
        mainWindow.getContentPane().add(bDisconnect);
        bDisconnect.setBounds(10, 40, 110, 25);

        bConnect.setBackground(new java.awt.Color(0,0,255));
        bConnect.setForeground(new java.awt.Color(255, 255, 255));
        bConnect.setText("CONNECT");
        mainWindow.getContentPane().add(bConnect);
        bConnect.setBounds(130, 40, 110, 25);

        bHelp.setBackground(new java.awt.Color(0,0,255));
        bHelp.setForeground(new java.awt.Color(255, 255, 255));
        bHelp.setText("HELP");
        mainWindow.getContentPane().add(bHelp);
        bHelp.setBounds(420, 40, 70, 25);

        bAbout.setBackground(new java.awt.Color(0,0,255));
        bAbout.setForeground(new java.awt.Color(255, 255, 255));
        bAbout.setText("ABOUT");
        mainWindow.getContentPane().add(bAbout);
        bAbout.setBounds(340, 40, 75, 25);

        lMessage.setText("Message: ");
        mainWindow.getContentPane().add(lMessage);
        lMessage.setBounds(10, 10, 60, 20);

        tfMessage.setForeground(new java.awt.Color(0, 0, 255));
        tfMessage.requestFocus();
        mainWindow.getContentPane().add(tfMessage);
        tfMessage.setBounds(40, 4, 260, 30);

        lConversation.setHorizontalAlignment(SwingConstants.CENTER);
        lConversation.setText("Conversations");
        mainWindow.getContentPane().add(lConversation);
        lConversation.setBounds(100, 70, 140, 16);

        taConversation.setColumns(20);
        taConversation.setFont(new java.awt.Font("Calibri", 0, 12));
        taConversation.setForeground(new java.awt.Color(0, 0, 255));
        taConversation.setLineWrap(true);
        taConversation.setRows(5);
        taConversation.setEditable(false);

        spConversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spConversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spConversation.setViewportView(taConversation);
        mainWindow.getContentPane().add(spConversation);
        spConversation.setBounds(10,90,330,180);

        lOnline.setHorizontalAlignment(SwingConstants.CENTER);
        lOnline.setText("Currently Online");
        lOnline.setToolTipText("");
        mainWindow.getContentPane().add(lOnline);
        lOnline.setBounds(350, 70, 130, 16);

        String [] testNames = {"Donald", "Goofy", "Mickey", "Pluto"};
        jlOnline.setForeground(new java.awt.Color(0, 0, 255));
        jlOnline.setListData(testNames);

        spOnline.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spOnline.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spOnline.setViewportView(jlOnline);
        mainWindow.getContentPane().add(spOnline);
        spOnline.setBounds(350,90,130,180);

        lLoggedInAs.setFont(new java.awt.Font("Calibri", 0, 12));
        lLoggedInAs.setText("Currently logged in as");
        mainWindow.getContentPane().add(lLoggedInAs);
        lLoggedInAs.setBounds(348, 0, 140, 15);

        lLoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        lLoggedInAsBox.setFont(new java.awt.Font("Calibri", 0, 12));
        lLoggedInAsBox.setForeground(new java.awt.Color(255, 0, 0));
        BorderFactory.createLineBorder(new java.awt.Color(0,0,0));
        mainWindow.getContentPane().add(lLoggedInAsBox);
        lLoggedInAsBox.setBounds(340, 17, 150, 20);
    }
    /*
     * log in action for log in button
     */
    public static void logInAction()
    {
        bEnter.addActionListener(
            new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    actionBEnter();
                }
            }
        );
    }

    public static void actionBEnter()
    {
        if(!tfUserNameBox.getText().equals(""))
        {
            userName = tfUserNameBox.getText().trim();
            lLoggedInAsBox.setText(userName);
            Chat_Server.currentUsers.add(userName);
            mainWindow.setTitle(userName + "'s chat box");
            loginWindow.setVisible(false);
            bSend.setEnabled(true);
            bDisconnect.setEnabled(true);
            bConnect.setEnabled(false);
            connect();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please enter a name");
        }
    }    
    /*
     * links an action to a button
     */
    public static void mainWindowAction()
    {
        bSend.addActionListener(
            new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    actionBSend();
                }
            }
        );

        bDisconnect.addActionListener(
            new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    actionBDisconnect();
                }
            }
        );

        bConnect.addActionListener(
            new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    buildLogInWindow();
                }
            }
        );

        bHelp.addActionListener(
            new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    actionBHelp();
                }
            }
        );

        bAbout.addActionListener(
            new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    actionBHelp();
                }
            }
        );
    }
    /*
     * sends the users message to the others on  the list
     */
    public static void actionBSend()
    {
        if(!tfMessage.getText().equals(""))
        {
            chatClient.send(tfMessage.getText());
            tfMessage.requestFocus();
        }
    }
    /*
     * disconnects user from the server
     */
    public static void actionBDisconnect()
    {
        try
        {
            chatClient.disconnect();
        }
        catch(Exception Y)
        {
            Y.printStackTrace();
        }
    }
    /*
     * unhelpful help button
     */
    public static void actionBHelp()
    {
        JOptionPane.showMessageDialog(null, "Multi-Client chat Program");
    }
}
