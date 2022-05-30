package edu.ufp.inf.sd.rmi.FroggerGame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameFactoryRI extends Remote {
    public boolean register(String username, String password) throws RemoteException;

    public SessionRI login(String username, String password) throws RemoteException;

    public DBMockup getDb() throws RemoteException;
}
