package edu.ufp.inf.sd.rmi.FroggerGame.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    public DBMockup dbMockup = new DBMockup();

    private HashMap<Integer, GamePartyRI> gameParties;

    public GameFactoryImpl() throws RemoteException {
        super();

        this.gameParties = new HashMap<>();
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        Boolean exists = dbMockup.exists(username, password);
        System.out.println(exists ? "Existe" : "Nao existe");

        if(exists)
            return false;

        dbMockup.register(username, password);

        return true;
    }

    @Override
    public SessionRI login(String username, String password) throws RemoteException
    {
        if (dbMockup.exists(username, password))
        {
            if (!this.dbMockup.getSessions().containsKey(username))
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sessão iniciada com sucesso!");
                SessionRI sessionRI = new SessionImpl(new User(username, password), this);
                this.dbMockup.getSessions().put(username, sessionRI);
                return sessionRI;
            }
            else
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sessão já iniciada!");
                return this.dbMockup.getSessions().get(username);
            }

        }
        return null;
    }

    public DBMockup getDb() throws RemoteException {
        return dbMockup;
    }

    public void addGameParty(GamePartyImpl gameParty) {
        this.gameParties.put(gameParty.getId(), gameParty);
    }

    public GamePartyRI getGameParty(Integer partyId){
        if(gameParties.containsKey(partyId)){
            return gameParties.get(partyId);
        }
        return null;
    }

    public void removeGameParty(int partyId){
        this.gameParties.remove(partyId);
    }

    public HashMap<Integer, GamePartyRI> getGameParties() {
        return gameParties;
    }
}