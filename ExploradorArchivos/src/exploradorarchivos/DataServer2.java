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
public class DataServer2 extends UnicastRemoteObject implements DataServer {
    private static DataServer replicadataserver2;
    public DataServer2(File directorio) throws RemoteException {
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
            directorio = new File("./Data/DataServer2");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1102);
            reg.rebind("DataServer2", new DataServer1(directorio));
            System.out.println("DataServer2 conectado");
        } catch (Exception e) {
            System.out.println(e);
        }
        //Porque el Dataserver2 se debe encargar de escribir la replica
        try {
            Registry reg5 = LocateRegistry.getRegistry("169.254.235.211",1103);
            replicadataserver2 = (DataServer) reg5.lookup("ReplicaDataServer2");
            System.out.println("Replica Data server 2 conectado");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    
}