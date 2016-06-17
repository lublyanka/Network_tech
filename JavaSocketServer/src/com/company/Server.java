package com.company;

import java.net.*;
import java.io.*;


class ServerThreads extends Thread
{
    private Socket socket = null;

    public ServerThreads(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try (
                InputStream inputStream = socket.getInputStream();             //Returns an input stream for this socket.
                OutputStream outputStream = socket.getOutputStream();)      //Returns an output stream for this socket.
        {


            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);
            String line;
            while (true)
            {
                ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
                int noThreads = currentGroup.activeCount();
                Thread[] lstThreads = new Thread[noThreads];
                currentGroup.enumerate(lstThreads);
                line = in.readUTF();
                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                int a = (int)Thread.currentThread().getId();
                for (int i = 0; i < noThreads; i++)
                    if ((int)lstThreads[i].getId()== 1)
                        out.writeUTF("200");
                    else
                        out.writeUTF(line);
                out.flush();
                System.out.println("Waiting for the next line...");
                System.out.println();

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

public class Server
{
    public ThreadGroup getTreadGroup()
    {
        return null;
    }

    public static void main(String[] args) throws IOException
    {
        int portNumber = 9595;
        int i = 0;
        boolean listening = true;
        Thread[] tarray;
        tarray = new Thread[1000];
        System.out.println("Hello, I'm the Socket Server.Waiting for connections");

        //Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
        try (ServerSocket serverSocket = new ServerSocket(portNumber))
        {
            while (listening)
            {
               new ServerThreads(serverSocket.accept()).start();


                    System.out.println("Woow! Client's here!");
                    System.out.println();

            }
        } catch (IOException e)
        {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);

        }
    }
}