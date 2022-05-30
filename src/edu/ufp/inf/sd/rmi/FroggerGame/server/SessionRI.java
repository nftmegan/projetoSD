package edu.ufp.inf.sd.rmi.FroggerGame.server;

import edu.ufp.inf.sd.rmi.FroggerGame.client.FroggerClientRI;
import edu.ufp.inf.sd.rmi.FroggerGame.client.PlayerRI;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;


public interface SessionRI extends Remote {
    public void logout(String username) throws RemoteException;

    public GamePartyRI createGameParty() throws RemoteException;
    public boolean joinGameParty(PlayerRI playerRI, int partyId) throws RemoteException;
    public boolean leaveGameParty() throws RemoteException;
    public boolean startGame() throws RemoteException;

    public HashMap<Integer, GamePartyRI> getGameParties() throws RemoteException;

    public User getUser() throws RemoteException;

    public void associateClient(FroggerClientRI clientRI) throws RemoteException;

    /*
    public void associateClient(JobShopClientRI clientRI) throws RemoteException;
    public void associateWorkers(WorkerRI workerRI, int jobID) throws IOException;
    public int getWorkersSize() throws RemoteException;
    public JobShopFactoryRI getJobShopFactoryImpl() throws RemoteException;
    public void changeJobGroupState(int jobID, int state) throws IOException;
    */

    public GameFactoryRI getGameFactoryImpl() throws RemoteException;

    public int getValue() throws RemoteException;
    public String getUname() throws RemoteException;
    public Boolean isPartyOwner() throws RemoteException;
}
