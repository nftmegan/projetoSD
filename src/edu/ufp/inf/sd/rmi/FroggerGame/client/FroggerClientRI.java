package edu.ufp.inf.sd.rmi.FroggerGame.client;

import edu.ufp.inf.sd.rmi.FroggerGame.server.GamePartyRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FroggerClientRI extends Remote {
    public void updateLobbyInfo(ArrayList<String> playerList) throws RemoteException;
    public PlayerRI getPlayerRI() throws RemoteException;
}
