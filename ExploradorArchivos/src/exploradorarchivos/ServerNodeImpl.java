/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploradorarchivos;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


/**
 *
 * @author Denisse Carbajal
 */
public class ServerNodeImpl extends UnicastRemoteObject implements ServerNode{
    private static HashMap<String, registro> registros = new HashMap();
    private static DataServer dataserver1;
    private static DataServer replicadataserver1;
    private static DataServer dataserver2;
    private static DataServer replicadataserver2;
     
    public ServerNodeImpl() throws RemoteException {
        super();
    }
    @Override
    public void mensajeServer(String mensaje) throws RemoteException {
        
    }

    @Override
    public boolean crearDirectorio(String nombre) throws RemoteException {
        return true;
    }

    @Override
    public boolean crearArchivo(String nombre, String Text) throws RemoteException {
       return true;
    }

    @Override
    public boolean borrarArchivo(String nombre) throws RemoteException {
       return true;
    }

    @Override
    public boolean borrarDirectorio(String nombre) throws RemoteException {
       return true;
    }

    @Override
    public String verArchivo(String nombre) throws RemoteException {
       return "";
    }
     private static void iniciar(){
        
       
        try {
            // create on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServerNode", new ServerNodeImpl()); 
            System.out.println("ServerNode iniciado"); 
            
            
            Registry reg2 = LocateRegistry.getRegistry("192.168.56.1", 1100); // agregar host
            dataserver1 = (DataServer) reg2.lookup("DataServer1");
            System.out.println("Data server 1 conectado");

            Registry reg3 = LocateRegistry.getRegistry("192.168.56.1",1101 );
            replicadataserver1 = (DataServer) reg3.lookup("ReplicaDataServer1");
            System.out.println("Replica Data server 1 conectado");

            Registry reg4 = LocateRegistry.getRegistry("192.168.56.1",1102);
            dataserver2 = (DataServer) reg4.lookup("DataServer2");
            System.out.println("Data server 2 conectado");
            
            Registry reg5 = LocateRegistry.getRegistry("192.168.56.1",1103);
            replicadataserver2 = (DataServer) reg5.lookup("ReplicaDataServer2");
            System.out.println("Replica Data server 2 conectado");
            
            dataserver1.Mensaje("Hola "+registros.get("DataServer1").getId());
            replicadataserver1.Mensaje("Hola "+registros.get("ReplicaDataServer1").getId());
            dataserver2.Mensaje("Hola "+registros.get("DataServer2").getId());
            replicadataserver2.Mensaje("Hola "+registros.get("ReplicaDataServer2").getId());
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }              
    }
    
    public static void main(String args[]) {
        registros.put("ServerNode", new registro(1));
        registros.put("DataServer1", new registro(2));
        registros.put("ReplicaDataServer1", new registro(3));
        registros.put("DataServer2", new registro(4));
        registros.put("ReplicaDataServer2", new registro(5));
        
        iniciar();   
    }  
  
}
