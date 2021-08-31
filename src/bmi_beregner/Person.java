package bmi_beregner;

import java.io.Serializable;

public class Person implements Serializable {

    public double weight;
    public double height;
    public double bmi;
    public String kropstype = "";


    void setBmi(){
        this.bmi = weight/((height/100)*(height/100));
        bodyType();
    }


     void bodyType(){
        if(bmi < 18.5){
            kropstype = "Undervægtig";
        }else if(bmi < 25){
            kropstype = "Sund og  normal";
        }else if(bmi < 30) {
            kropstype = "Overvægtig";
        }else if(bmi > 30){
            kropstype = "Svært overvægtig";
        }
    }


}
