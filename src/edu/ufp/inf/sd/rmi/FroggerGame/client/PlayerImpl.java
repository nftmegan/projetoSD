package edu.ufp.inf.sd.rmi.FroggerGame.client;

import frogger.Main;

import edu.ufp.inf.sd.rmi.FroggerGame.server.GamePartyRI;

import java.rmi.RemoteException;

public class PlayerImpl implements PlayerRI {
    private int id;
    private String username;

    private FroggerClientRI owner;
    private GamePartyRI gamePartyRI;

    public PlayerImpl(String username, FroggerClientRI froggerClientRI) {
        this.username = username;
        this.owner = froggerClientRI;
    }

    public void associateGameParty(GamePartyRI gamePartyRI) throws RemoteException{
        this.gamePartyRI = gamePartyRI;
    }

    public GamePartyRI getGameParty() {
        return gamePartyRI;
    }

    public FroggerClientRI getOwner() throws RemoteException {
        return owner;
    }

    public String getUsername() throws RemoteException {
        return username;
    }

    public String getId() throws RemoteException {
        return username;
    }

    public void setId(int id) throws RemoteException {
        this.id = id;
    }

    public void gameStart() throws RemoteException {
        System.out.println("Checgou aqui");
        Main f = new Main();
        f.run();
    }
}