/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploradorarchivos;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Denisse Carbajal
 */
public class ReplicaDataServer1 extends UnicastRemoteObject implements DataServer {

    public ReplicaDataServer1(File directorio) throws RemoteException {
        super();
        this.directorio = directorio;
        
    }
     
    @Override
    public void Mensaje(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }
    private static File directorio;
    private static Registry reg;
    
    public static void main(String args[]) {
        try {
            directorio = new File("./Data/ReplicaDataServer1");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1101);
            reg.rebind("ReplicaDataServer1", new DataServer1(directorio));
            System.out.println("ReplicaDataServer1 conectado");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}