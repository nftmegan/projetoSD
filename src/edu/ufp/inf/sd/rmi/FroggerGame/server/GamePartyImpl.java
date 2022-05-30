package edu.ufp.inf.sd.rmi.FroggerGame.server;

import edu.ufp.inf.sd.rmi.FroggerGame.client.FroggerClientImpl;
import edu.ufp.inf.sd.rmi.FroggerGame.client.FroggerClientRI;
import edu.ufp.inf.sd.rmi.FroggerGame.client.PlayerRI;
import edu.ufp.inf.sd.rmi.FroggerGame.util.threading.ThreadPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamePartyImpl implements GamePartyRI, Serializable {
    private int id;
    private FroggerClientRI client;
    private int state; // 1 - Lobby, 2 - Started, 0 - Ended

    private ArrayList<PlayerRI> playerList = new ArrayList<>();

    public GamePartyImpl(int id, FroggerClientRI client) {
        this.id = id;
        this.client = client;
        this.state = 1;
    }

    public int getId() {
        return id;
    }

    public int getPartySize() {
        return playerList.size();
    };

    public void changeState(int state) throws RemoteException {
        this.state = state;
    }

    public void attachPlayer(PlayerRI playerRI) throws RemoteException {
        System.out.println("ATTACHED A CLIENT IN THE GAME PARTY SERVER SIDE");
        playerList.add(playerRI);
        notifyLobbyChange();
    };

    public void detachPlayer(PlayerRI playerRI) throws RemoteException {
        System.out.println("DETACHED A CLIENT IN THE GAME PARTY SERVER SIDE");
        playerList.remove(playerRI);
        notifyLobbyChange();
    };

    public void startGame() throws RemoteException {
        notifyGameStarted();
    }

    public void notifyLobbyChange() throws RemoteException {
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < playerList.size(); i++) {
            list.add(playerList.get(i).getUsername());
        }

        for (PlayerRI playerRI : playerList) {
            System.out.println("Notifiying one");
            playerRI.getOwner().updateLobbyInfo(list);
        }
    }

    public void notifyGameStarted() throws RemoteException {
        for (PlayerRI playerRI : playerList) {
            System.out.println("Notifiying game started to one");
            playerRI.gameStart();
        }
    }

    public FroggerClientRI getClient() {
        return client;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}