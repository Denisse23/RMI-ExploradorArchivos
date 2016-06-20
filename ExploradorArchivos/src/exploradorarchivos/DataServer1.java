/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploradorarchivos;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Denisse Carbajal
 */
public class DataServer1 extends UnicastRemoteObject implements DataServer {
    private static DataServer replicadataserver1;
    public DataServer1(File directorio) throws RemoteException {
        super();
        this.directorio = directorio;
        
    }
     
    @Override
    public void Mensaje(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }
    private static File directorio;
    private static Registry reg;
    
    public static void main(String args[]){

        try {
            directorio = new File("./Data/DataServer1");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1100);
            reg.rebind("DataServer1", new DataServer1(directorio));
            System.out.println("DataServer1 conectado");
        } catch (Exception e) {
            System.out.println(e);
        }
        //POrque el Dataserver1 se debe encargar de escribir la replica
        try {
            Registry reg3 = LocateRegistry.getRegistry("169.254.235.211",1101);
            replicadataserver1 = (DataServer) reg3.lookup("ReplicaDataServer1");
            System.out.println("Replica Data server 1 conectado");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    

    
}
