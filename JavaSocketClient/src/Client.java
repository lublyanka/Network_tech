import java.net.*;
import java.io.*;

public class Client
{
    public static void main(String[] ar)
    {
        int serverPort = 9595; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try
        {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);

            // Создаем поток для чтения с клавиатуры.
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String lineIn,lineOut;
            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();

            while (true)
            {
                lineOut = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                lineIn = in.readUTF();
                System.out.println("Sending this line to the server...");
                out.writeUTF(lineOut); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                 // ждем пока сервер отошлет строку текста.
                if(lineIn.contentEquals("200"))
                    System.out.println("Well done! Broadcast message was sent to others!");
                else
                    System.out.println("Ooops! There are no other clients on the server at this moment.Message didn't sent");
                System.out.println();
            }
        } catch (Exception x)
        {
            x.printStackTrace();
        }
    }
}

