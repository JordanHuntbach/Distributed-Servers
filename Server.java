import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {

    public static void main(String[] args){
        try{
            // Sets up socket.
            ServerSocket serverSocket = new ServerSocket(8003);
            while (true) {
                // Establishes connection with client.
                Socket socket = serverSocket.accept();
                System.out.println("Connection Made");
                // Gets data from client.
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                // Gets string representation of the data.
                String str = dataInputStream.readUTF();
                if (str.equals("exit")) {
                    break;
                } else if (str.equals("workload")) {
                    // Returns the workload to the client.
                    // This is just a random number, for the purpose of modelling an actual distributed system.
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    Random rand = new Random();
                    int work = rand.nextInt(50);
                    dataOutputStream.writeUTF(String.valueOf(work));
                    dataOutputStream.flush();

                    // Closes socket.
                    dataOutputStream.close();
                    socket.close();
                    System.out.println("Connection Closed\n\n");
                } else {
                    // Prints message
                    System.out.println("Numbers: " + str);

                    // Convert string to numbers
                    str = str.replace("[", "").replace("]", "");
                    String[] numbers;
                    numbers = str.split(",");
                    float num1 = new Float(numbers[0]);
                    float num2 = new Float(numbers[1]);
                    float num3 = new Float(numbers[2]);
                    float num4 = new Float(numbers[3]);
                    float num5 = new Float(numbers[4]);

                    // Do the maths
                    float sum = num1 + num2 + num3 + num4 + num5;
                    float average = sum / 5;
                    float maximum = num1;
                    if (num2 > maximum) {
                        maximum = num2;
                    }
                    if (num3 > maximum) {
                        maximum = num3;
                    }
                    if (num4 > maximum) {
                        maximum = num4;
                    }
                    if (num5 > maximum) {
                        maximum = num5;
                    }
                    System.out.println("Sum: " + sum);
                    System.out.println("Average: " + average);
                    System.out.println("Max: " + maximum);

                    // Returns the results to the client.
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("Sum: " + sum);
                    dataOutputStream.writeUTF("Average: " + average);
                    dataOutputStream.writeUTF("Max: " + maximum);
                    dataOutputStream.flush();

                    // Closes socket.
                    dataOutputStream.close();
                    System.out.println("Connection Closed\n\n");
                }
            }
            serverSocket.close();
        } catch (EOFException e) {
            System.out.print("Server Shutting Down.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}