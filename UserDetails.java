/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 *
 * @author Mattafire
 */
@Entity
public class UserDetails {
    @Id
    private int userId;
    @Column (name="UserName")
    private String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
   
}
