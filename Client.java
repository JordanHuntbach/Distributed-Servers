import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            /* Pick a port from 8000 to 8010 to connect to (representing a distributed system)
             * TODO: The client can see that there are different sockets to connect to. We should instead implement
             * TODO: replication transparency, whereby the client doesn't know this - not sure how at the moment.
             */
            int port = 0;
            int min = 100000;
            for (int i = 8000; i < 8010; i++) {
                try {
                    Socket socket = new Socket("localhost", i);
                    // Sets up output stream to socket.
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    // Writes message to socket.
                    dataOutputStream.writeUTF("workload");
                    // Sends message from socket.
                    dataOutputStream.flush();

                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    int work = Integer.valueOf(dataInputStream.readUTF());
                    System.out.println("Port " + i + " workload: " + work);
                    if (work < min) {
                        min = work;
                        port = i;
                    }
                } catch (ConnectException e) {
                    System.out.println("Cannot connect to port " + i);
                }
            }
            if (port == 0) {
                System.out.println("Cannot connect on any ports.");
            } else {
                System.out.println("Using port: " + port);
                Socket socket = new Socket("localhost", port);

                // Gets user input
                ArrayList<Float> numbers = new ArrayList<>();
                int i;
                Scanner reader = new Scanner(System.in);
                while ((i = numbers.size()) < 5) {
                    System.out.print("Enter number " + (i + 1) + ": ");
                    float number = reader.nextFloat();
                    numbers.add(number);
                }
                reader.close();

                // Sets up output stream to socket.
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                // Writes message to socket.
                dataOutputStream.writeUTF(numbers.toString());
                // Sends message from socket.
                dataOutputStream.flush();

                // Gets result back from server.
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String sum = dataInputStream.readUTF();
                System.out.println(sum);
                String avg = dataInputStream.readUTF();
                System.out.println(avg);
                String max = dataInputStream.readUTF();
                System.out.println(max);

                // Closes output stream.
                dataOutputStream.close();
                // Closes socket.
                // socket.close();
            }
        } catch (InputMismatchException e) {
            System.out.print("You must enter numbers only.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}