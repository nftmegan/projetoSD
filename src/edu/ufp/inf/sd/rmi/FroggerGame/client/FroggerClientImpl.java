package edu.ufp.inf.sd.rmi.FroggerGame.client;

import edu.ufp.inf.sd.rmi.FroggerGame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.FroggerGame.server.SessionRI;
import edu.ufp.inf.sd.rmi.FroggerGame.server.GamePartyRI;

import edu.ufp.inf.sd.rmi.FroggerGame.server.User;
import edu.ufp.inf.sd.rmi.FroggerGame.util.rmisetup.SetupContextRMI;
//import edu.ufp.inf.sd.rmi.FroggerGame.util.threading.ThreadPool;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FroggerClientImpl implements FroggerClientRI {

    private final SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private GameFactoryRI gameFactoryRI;
    private SessionRI sessionRI = null;
    private PlayerImpl playerImpl = null;

    private ArrayList<String> lobbyInfo = new ArrayList<>();

    private User user = null;

    public FroggerClientImpl(SetupContextRMI contextRMI) throws RemoteException {
        this.contextRMI = contextRMI;
        lookupService();
        Remote exportObject = java.rmi.server.UnicastRemoteObject.exportObject(this, 0);
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                gameFactoryRI = (GameFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return gameFactoryRI;
    }

    public void playService() {
        ClientUI.startUI(this);
    }

    public boolean createGamePartyRequest() {
        GamePartyRI gamePartyRI = null;

        System.out.println("CLIEMT: CREATING A NEW GAME PARTY");

        try {
            gamePartyRI = sessionRI.createGameParty();
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        if(gamePartyRI == null)
            return false;

        System.out.println("CLIEMT: CREATING A NEW GAME PARTY: GOT A GAME PARTY RI BACK, JOINING OUR GAME");

        try {
            return joinGamePartyRequest(gamePartyRI.getId());
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean joinGamePartyRequest(int id) {
        System.out.println("CLIEMT: JOINING A GAME PARTY");
        playerImpl = new PlayerImpl(user.getUsername(), this);

        try {
            return sessionRI.joinGameParty(playerImpl, id);
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void leaveGamePartyRequest() {
        try {
            sessionRI.leaveGameParty();
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        playerImpl = null;
    }

    public void startGameRequest() {
        try {
            sessionRI.startGame();
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        playerImpl = null;
    }

    public void registerRequest(String username, String password) {
        boolean success = false;
        try {
            success = gameFactoryRI.register(username, password);
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        if(success)
            loginRequest(username, password);
    }

    public void loginRequest (String username, String password) {
        try {
            sessionRI = gameFactoryRI.login(username, password);;
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        if(sessionRI == null) {
            System.out.println("ERROR LOGIN IN");
            return;
        }

        try {
            sessionRI.associateClient(this);
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        user = new User(username, password);
    }

    public void logoutRequest () {
        if(sessionRI == null)
            return;

        try {
            sessionRI.logout(this.sessionRI.getUname());
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }

        user = null;
        sessionRI = null;

        System.out.println("LOGGED OUT SUCCESSFULY");
    }

    public Boolean isPartyOwnerRequest() {
        if(sessionRI == null)
            return false;

        try {
            return sessionRI.isPartyOwner();
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateLobbyInfo(ArrayList<String> playerList) throws RemoteException {
        lobbyInfo = playerList;
        ClientUI.UpdateUI();
    }

    public SessionRI getSession() {
        return sessionRI;
    }

    public PlayerRI getPlayerRI() {
        return playerImpl;
    }

    public void getState(String error) throws RemoteException{
        System.out.println(error);
    }

    public HashMap<Integer, GamePartyRI> getGameParties() {
        if(sessionRI == null)
            return null;

        try {
            return sessionRI.getGameParties();
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<String> getLobbyInfo()  {
        return lobbyInfo;
    }
}
