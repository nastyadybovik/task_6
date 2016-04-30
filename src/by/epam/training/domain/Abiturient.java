package by.epam.training.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Настенька on 11/9/2015.
 */
public class Abiturient implements Serializable{

    private static final long serialVersionID = 1L;

    private int ID;
    private String first_name;
    private String last_name;
    private String patronymic;
    private Date date_of_birth;
    private String passport_data;
    private String adress;

    public Abiturient() {
        super();
    }

    public Abiturient(int ID, String first_name, String last_name, String patronymic, Date date_of_birth, String passport_data, String adress) {
        this.ID = ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.date_of_birth = date_of_birth;
        this.passport_data = passport_data;
        this.adress = adress;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPassport_data() {
        return passport_data;
    }

    public void setPassport_data(String passport_data) {
        this.passport_data = passport_data;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
