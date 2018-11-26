package com.rsvp.Gui;

import com.rsvp.hibernate.entity.Invite;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Functions implements Initializable {

    @FXML public Button refreshBtn;
    @FXML public ListView<String> dbList;
    @FXML public TextField selKey;
    @FXML public TextField nameTF;

    private SessionFactory f;
    private Session sess;

    private ArrayList<String> inviteesNames = new ArrayList<>();
    private ArrayList<Integer> inviteesKey = new ArrayList<>();
    private ArrayList<String> inviteesCodes = new ArrayList<>();

    private int selIndex;
    private int selectedKey;

    //Uploads name and unique Invite Code to database (CREATE)
    @FXML
    void giveInvitation(){
        String x = nameTF.getText().trim();
        if(x.length()>0){
            //Generate random alphanumeric code
            String code;
            do {code = RandomStringUtils.randomAlphanumeric(7);} while(inviteesCodes.contains(code));

            initializeSession();
            Invite invite = new Invite(x);
            invite.setInvite_code(code);

            sess.save(invite);
            sess.getTransaction().commit();

            nameTF.clear();
            listRefresh();

        } else System.out.println("No name detected");
    }

    //Deletes entry from database (DELETE)
    @FXML
    void deleteInvitee(){
        if(selectedKey<1) System.out.println("No selection detected");
        else{
            initializeSession();
            Invite invite = sess.get(Invite.class, selectedKey);
            sess.delete(invite);

            sess.getTransaction().commit();
            listRefresh();
        }
    }

    //Changes invite code (UPDATE)
    @FXML
    void changeCode(){
        if(selectedKey<1) System.out.println("No selection detected");
        else {
            initializeSession();
            Invite in = sess.get(Invite.class, selectedKey);

            String code;
            do {code = RandomStringUtils.randomAlphanumeric(7);} while(inviteesCodes.contains(code));

            in.setInvite_code(code);
            sess.getTransaction().commit();

            listRefresh();
        }
    }

    //Refreshes List View
    @FXML
    void listRefresh(){
        //clears list
        getList();
        dbList.getItems().clear();

        System.out.println("Number of inviteesNames: "+ inviteesNames.size());

        String name;
        String key;
        String code;
        String print;
        for(int x=0;x<inviteesNames.size();x++){
            name = inviteesNames.get(x);
            key = String.valueOf(inviteesKey.get(x));
            code = inviteesCodes.get(x);
            print = "["+key+"] "+name+", "+code;

            dbList.getItems().add(print);
        }

    }

    //Invitee ArrayLists update (READ)
    private void getList(){
        inviteesNames.clear();
        inviteesKey.clear();
        inviteesCodes.clear();

        initializeSession();
        try {
            Invite x;

            for(int z=1; z<=50; z++){
                x = sess.get(Invite.class, z);
                if(x!=null) {
                    inviteesNames.add(x.getName());
                    inviteesKey.add(x.getId());
                    inviteesCodes.add(x.getInvite_code());
                }
            }

        } catch (Exception e) {
            //System.out.println(e.getMessage());
        } finally {
            sess.getTransaction().commit();

            System.out.println("Printing names in inviteesNames list");
            System.out.println(inviteesNames);
        }

    }

    //Initialize SessionFactory
    private void initializeSession(){
        // Create Session
        sess = f.getCurrentSession();
        sess.beginTransaction();
    }

    //testing function
    @FXML
    void test(){
        initializeSession();
        Invite in;
        for(int x = 1; x<50;x++){
            in = sess.get(Invite.class, x);
            if(in!=null){
                System.out.println(String.valueOf(in.getId())+in.getName()+in.getInvite_code());
            }
        }
        sess.getTransaction().commit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create Session Factoru
        f = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Invite.class).buildSessionFactory();
        System.out.println("SessionFactory Initialized!");

        //Index listener for listview as reference to get PRIMARY KEY id from inviteesKey list
        dbList.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
            selIndex = (int) newValue;

            try{
                selectedKey = inviteesKey.get(selIndex);
            }catch(Exception e){selectedKey=0;}

            selKey.setText(String.valueOf(selectedKey));
        });

    }
}
