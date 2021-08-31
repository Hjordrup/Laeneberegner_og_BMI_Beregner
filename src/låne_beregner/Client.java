package låne_beregner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    DataOutputStream toServer;
    DataInputStream fromServer;

    @Override
    public void start(Stage primaryStage) throws Exception {


        TextArea textArea = new TextArea();
        Label interestLabel = new Label("Annual Interest Rate");
        Label yearsLabel = new Label("Number of Years");
        Label loanLabel = new Label("Loan Amount");

        TextField interestField = new TextField();
        TextField yearsField = new TextField();
        TextField loanField = new TextField();

        Button bt = new Button("Submit");
        Pane pane = new Pane();

        VBox vBox1 = new VBox();
        vBox1.getChildren().add(0, interestLabel);
        vBox1.getChildren().add(1, yearsLabel);
        vBox1.getChildren().add(2, loanLabel);

        vBox1.setSpacing(7);


        VBox vBox2 = new VBox();
        vBox2.getChildren().add(0, interestField);
        vBox2.getChildren().add(1, yearsField);
        vBox2.getChildren().add(2, loanField);

        HBox topHbox = new HBox();

        topHbox.getChildren().add(0, vBox1);
        topHbox.getChildren().add(1, vBox2);
        topHbox.getChildren().add(2, bt);

        topHbox.setAlignment(Pos.CENTER_LEFT);

        VBox overallVbox = new VBox();
        overallVbox.getChildren().add(0, topHbox);
        overallVbox.getChildren().add(1, textArea);

        pane.getChildren().add(overallVbox);


        Scene scene = new Scene(pane, 500, 200);

        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();


        bt.setOnAction(e->
        {

            try {
                Socket socket = new Socket("localhost", 8080);

                toServer = new DataOutputStream(socket.getOutputStream());
                fromServer = new DataInputStream(socket.getInputStream());

                toServer.writeDouble(Double.parseDouble(interestField.getText()));
                toServer.writeDouble(Double.parseDouble(yearsField.getText()));
                toServer.writeDouble(Double.parseDouble(loanField.getText()));
                toServer.flush();

                double montPayment = fromServer.readDouble();
                double total = fromServer.readDouble();




                Platform.runLater(()-> {
                    textArea.appendText("Annual interest Rate: " + interestField.getText() +
                            " \n Number of years: " + yearsField.getText() +
                            " \n Loan amount: " + loanField.getText() +
                            " \n monthlyPayment: " + montPayment +
                            " \n totalPayment: " + total  + " \n");
                });







            } catch (IOException exception) {
                exception.printStackTrace();
            }
            });




    }
}



