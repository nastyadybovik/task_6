package by.epam.training.domain;

import java.io.Serializable;

/**
 * Created by Настенька on 11/6/2015.
 */
public class User  implements Serializable{

    private static final long serialVersionID = 1L;

    private Long ID;
    private String login;
    private String password;

    public User(Long ID, String login, String password) {
        this.ID = ID;
        this.login = login;
        this.password = password;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": ID="+ID);
        sb.append(", login="+login);
        sb.append(", password="+password);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }
        User o = (User)obj;
        if(ID != o.ID){
            return false;
        }
        if(login != o.login){
            if(!login.equals(o.login)){
                return false;
            }
        }
        if(password != o.password){
            if(!password.equals(o.password)){
                return false;
            }
        }
        return true;
    }
}
