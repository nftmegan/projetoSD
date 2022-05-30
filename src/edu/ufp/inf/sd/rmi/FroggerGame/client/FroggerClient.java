package edu.ufp.inf.sd.rmi.FroggerGame.client;

import frogger.Main;
import edu.ufp.inf.sd.rmi.FroggerGame.util.rmisetup.SetupContextRMI;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class FroggerClient {

    /**
     * Context for connecting a RMI client MAIL_TO_ADDR a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */

    private FroggerClientImpl froggerClientImpl;

    public static void main(String[] args) {
        System.out.println("teste");
        //Main.start();
        try{
            Main f = new Main();
            f.run();
        }catch (ExceptionInInitializerError e){
            e.printStackTrace();
        }

/*
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi.PROJETO.server.Project <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            FroggerClient hwc = new FroggerClient(args);
            //2. ============ Lookup service ============
        }

 */
    }


    public FroggerClient(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
            this.froggerClientImpl = new FroggerClientImpl(contextRMI);
            this.froggerClientImpl.playService();
        } catch (RemoteException e) {
            Logger.getLogger(FroggerClientImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
