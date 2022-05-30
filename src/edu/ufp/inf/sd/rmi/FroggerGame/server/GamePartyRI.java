package edu.ufp.inf.sd.rmi.FroggerGame.server;

import edu.ufp.inf.sd.rmi.FroggerGame.client.FroggerClientRI;
import edu.ufp.inf.sd.rmi.FroggerGame.client.PlayerRI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GamePartyRI extends Remote, Serializable
{
    public int getId() throws RemoteException;
    public int getPartySize();
    public void changeState(int state) throws IOException;

    //public void notifyAllWorkers() throws RemoteException;

    //public User getUser() throws RemoteException;

    public void attachPlayer(PlayerRI playerRI) throws RemoteException;
    public void detachPlayer(PlayerRI playerRI) throws RemoteException;

    public void startGame() throws RemoteException;

    public FroggerClientRI getClient();

    public int getState();

    public void setState(int state);
}
