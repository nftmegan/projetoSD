package edu.ufp.inf.sd.rmi.FroggerGame.client;

import edu.ufp.inf.sd.rmi.FroggerGame.server.GamePartyImpl;
import edu.ufp.inf.sd.rmi.FroggerGame.server.GamePartyRI;
import edu.ufp.inf.sd.rmi.FroggerGame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.FroggerGame.server.SessionRI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerRI extends Remote, Serializable {
    public void associateGameParty(GamePartyRI gamePartyRI) throws RemoteException;
    public String getUsername() throws RemoteException;
    public FroggerClientRI getOwner() throws RemoteException;

    public void setId(int id) throws RemoteException;

    public void gameStart() throws RemoteException;
}