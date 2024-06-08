import java.util.Scanner;

public class ClientConnection {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ClientConnection <port>");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Port must be a number.");
            return;
        }

        MySocket mySocket = new MySocket("localhost", port);
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your nick: ");
        String nick = scanner.nextLine();
        
        SwingClient client = new SwingClient(nick, mySocket);
        scanner.close();
        
        client.createAndShowGUI(nick);

        
        new Thread(new Runnable() {
            public void run() {
                String message;
                while ((message = mySocket.readLine()) != null) {
                    client.addMessage(message);
                }
            }
        }).start();
    }
}
