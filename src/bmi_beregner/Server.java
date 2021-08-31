package bmi_beregner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {


    public static void main(String[] args) {
        new Server();
    }




      public Server(){
            try{
                ServerSocket serverSocket = new ServerSocket(8080);
                 System.out.println(new Date() +
                        ": Server started at socket 8080\n");




                while(true){
                    Socket socket = serverSocket.accept();
                    System.out.println( "Connected to a client"
                                + new Date() + "\n");
                    new Thread(new HandleAClient(socket)).start();
                }
           }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }




    class HandleAClient implements Runnable {
        private Socket socket; // A connected socket

        /** Construct a thread */
        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

        public void run(){
            try {

                ObjectInputStream inputFromClient = new ObjectInputStream(
                        socket.getInputStream());
                ObjectOutputStream outputToClient = new ObjectOutputStream(
                        socket.getOutputStream());


                while(true){
                    Person person = (Person) inputFromClient.readObject();
                    person.setBmi();

                    outputToClient.writeObject(person);
                    outputToClient.flush();

                    System.out.println("Weight: " + person.weight +
                            " \n Height: " + person.height +
                            "\n BMI: " + person.bmi +
                            "\n Kropstype: " + person.kropstype);
                }

            }catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }

        }
    }




