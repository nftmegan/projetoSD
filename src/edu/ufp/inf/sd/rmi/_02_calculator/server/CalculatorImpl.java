package edu.ufp.inf.sd.rmi._02_calculator.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI {

    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double add(ArrayList<Double> list) throws RemoteException {
        double res = 0;
        for(int i = 0; i < list.size(); i++)
            res += list.get(i);
        return res;
    }

    @Override
    public double div(double a, double b) throws RemoteException {
        return a / b;
    }
}
