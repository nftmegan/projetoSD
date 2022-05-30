package edu.ufp.inf.sd.rmi.FroggerGame.server;

import edu.ufp.inf.sd.rmi.FroggerGame.client.FroggerClientRI;
import edu.ufp.inf.sd.rmi.FroggerGame.client.PlayerRI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionImpl extends UnicastRemoteObject implements SessionRI, Serializable {
    private final GameFactoryImpl gameFactoryImpl;
    private User user;
    private FroggerClientRI clientRI;
    private GamePartyRI gamePartyRI = null;
    private PlayerRI playerRI = null;

    public SessionImpl(User user, GameFactoryImpl gameFactoryImpl) throws RemoteException {
        super();
        this.user = user;
        this.gameFactoryImpl = gameFactoryImpl;
    }

    @Override
    public void logout(String username) throws RemoteException {
        if (this.gameFactoryImpl.getDb().getSessions().containsKey(username)) {
            this.gameFactoryImpl.getDb().getSessions().remove(username);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LOGOUT efetuado pelo Utilizador @ {0}", username);
        }

        if(leaveGameParty())
            System.out.println("Left an existing game");
    }

    public GamePartyRI createGameParty() throws RemoteException {
        int partyID = gameFactoryImpl.getGameParties().size() + 1;
        GamePartyImpl newGameParty = new GamePartyImpl(partyID, this.clientRI);

        System.out.println("CREATED GAME PARTY id:" + partyID);
        this.gameFactoryImpl.addGameParty(newGameParty);

        return newGameParty;
    }

    public boolean joinGameParty(PlayerRI newPlayerRI, int id) throws RemoteException {
        if(!gameFactoryImpl.getGameParties().containsKey(id))
            return false;

        playerRI = newPlayerRI;
        gamePartyRI = gameFactoryImpl.getGameParty(id);

        playerRI.setId(gameFactoryImpl.getGameParties().size() + 1);
        playerRI.associateGameParty(gameFactoryImpl.getGameParty(id));

        gameFactoryImpl.getGameParty(id).attachPlayer(playerRI);

        return true;
    }

    public boolean leaveGameParty() throws RemoteException {
        if(!gameFactoryImpl.getGameParties().containsKey(gamePartyRI.getId()) || playerRI == null)
            return false;

        gameFactoryImpl.getGameParty(gamePartyRI.getId()).detachPlayer(playerRI);
        playerRI = null;


        if(gameFactoryImpl.getGameParty(gamePartyRI.getId()).getPartySize() == 0) {
            gameFactoryImpl.getGameParties().remove(gamePartyRI.getId());
        }
        gamePartyRI = null;

        return true;
    }

    public boolean startGame() throws RemoteException {
        if(gamePartyRI == null)
            return false;

        if(!isPartyOwner())
            return false;

        gamePartyRI.startGame();

        return true;
    }

    public HashMap<Integer, GamePartyRI> getGameParties() throws RemoteException {
        return gameFactoryImpl.getGameParties();
    }

    public void associateClient(FroggerClientRI clientRI) throws RemoteException {
        this.clientRI = clientRI;
    }

    public User getUser() {
        return user;
    }

    public String getUname() throws RemoteException {
        return user.getUsername();
    };

    public void setUser(User user) {
        this.user = user;
    }

    public int getValue() throws RemoteException {
        return 5;
    }

    public GameFactoryImpl getGameFactoryImpl() throws RemoteException {
        return gameFactoryImpl;
    }

    public Boolean isPartyOwner() throws RemoteException {
        if(gamePartyRI == null)
            return false;

        return clientRI == gamePartyRI.getClient();
    }
}
