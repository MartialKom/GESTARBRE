package cm.pfe.gestarbre.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Utilisateur {

    public String nom;
    public String prenom;
    public String login;
    private String mdp;

    public Utilisateur(String nom, String prenom, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
    }



    public Utilisateur(String login, String mdp){

        this.login = login;
        this.mdp = mdp;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }


    public int connection ( Connection c){

    String req = "Select * from utilisateur where login=?";
    String req2 = "select mdp from utilisateur where login = ?";



        try{

            PreparedStatement prepare = c.prepareStatement(req);
            prepare.setString(1,this.getLogin());

            ResultSet r = prepare.executeQuery();

            if(!r.next()){
                return 0;
            }
            else{

                PreparedStatement prepare2 = c.prepareStatement(req2);
                prepare2.setString(1,this.getLogin());

                ResultSet r2= prepare2.executeQuery();

                String mdp = r2.getString("mdp");


                if(mdp.equals(this.getMdp()))
                return 1;

                else
                    return 0;
            }

        }catch (SQLException e){
            return 3;
        }


    }


}
