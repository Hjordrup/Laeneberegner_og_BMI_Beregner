package bmi_beregner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    ObjectOutputStream toServer = null;
    ObjectInputStream fromServer = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        TextArea textArea = new TextArea();
        Label kgLabel = new Label("WEIGHT IN KG");
        Label heightLabel = new Label("HEIGHT IN CM");
        TextField kgField = new TextField();
        TextField heoghtField = new TextField();
        Button bt = new Button("Submit");
        Pane pane = new Pane();

        VBox vBox1 = new VBox();
        vBox1.getChildren().add(0, kgLabel);
        vBox1.getChildren().add(1, heightLabel);

        VBox vBox2 = new VBox();
        vBox2.getChildren().add(0, kgField);
        vBox2.getChildren().add(1, heoghtField);

        HBox topHbox = new HBox();

        topHbox.getChildren().add(0, vBox1);
        topHbox.getChildren().add(1, vBox2);
        topHbox.getChildren().add(2, bt);

        VBox overallVbox = new VBox();
        overallVbox.getChildren().add(0, topHbox);
        overallVbox.getChildren().add(1, textArea);

        pane.getChildren().add(overallVbox);


        Scene scene = new Scene(pane, 500, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();




        bt.setOnAction(e->{
            Person person = new Person();
            person.weight = Double.parseDouble(kgField.getText());
            person.height = Double.parseDouble(heoghtField.getText());


            try {
                Socket socket = new Socket("localhost", 8080);

                toServer = new ObjectOutputStream(socket.getOutputStream());
                fromServer = new ObjectInputStream(socket.getInputStream());

                toServer.writeObject(person);
                toServer.flush();

                Person fromServerPerson = (Person) fromServer.readObject();
                Platform.runLater(()-> {
                    textArea.appendText("Weight: " + fromServerPerson.weight +
                            " \n Height: " + fromServerPerson.height +
                            " \n BMI: " + fromServerPerson.bmi +
                            " \n Kropstype: " + fromServerPerson.kropstype);
                });

            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();

            }



        });





    }
}




