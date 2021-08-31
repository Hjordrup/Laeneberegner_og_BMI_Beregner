package låne_beregner;



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



     class HandleAClient implements Runnable {

        Socket socket;

        HandleAClient(Socket socket){ this.socket = socket; }


        @Override
        public void run() {
            try {

                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());


                while(true){

                    double intRate = (inputFromClient.readDouble()/1200);
                    double years = inputFromClient.readDouble();
                    double loanAmount = inputFromClient.readDouble();




                    double mont =  loanAmount * intRate / (1
                            - 1 / Math.pow(1 + intRate, years * 12));
                    double total = mont*years*12;
                            outputToClient.writeDouble(mont);
                    outputToClient.writeDouble(total);
                    outputToClient.flush();









                }

            }catch (IOException exception) {
                exception.printStackTrace();
            }




        }
    }





}
