/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploradorarchivos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


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
    private static DefaultTreeModel directorio;
     
    public ServerNodeImpl() throws RemoteException {
        super();
        directorio = new DefaultTreeModel(new DefaultMutableTreeNode(new NodoArbol("root",-1,'d')));
        File archivo = null;
        try{
            archivo = new File ("directorio.bin");
            if (!archivo.exists()){
                FileOutputStream salida = new FileOutputStream(archivo);
                ObjectOutputStream objeto = new ObjectOutputStream(salida);
                objeto.writeObject(directorio);
                objeto.flush();
                objeto.close();
                salida.close();
            }else{
                FileInputStream entrada = new FileInputStream(archivo);
                ObjectInputStream objeto = new ObjectInputStream(entrada);
                try{
                    directorio = (DefaultTreeModel)objeto.readObject();
                }catch(ClassNotFoundException e){
                    //encontro el final del binario
                }catch (EOFException e){
                }
                finally{
                    objeto.close();
                    entrada.close();
                }
            }
        }catch(Exception e){
        
        }
    }
    
    
    @Override
    public void mensajeServer(String mensaje) throws RemoteException {
        
    }
    
    private TreePath find(DefaultMutableTreeNode root, DefaultMutableTreeNode search) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (((NodoArbol)node.getUserObject()).getNombre().equals(((NodoArbol)search.getUserObject()).getNombre())) {
                return new TreePath(node.getPath());
            }
        }        
        return null;
    }

    @Override
    public boolean crearDirectorio(String nombre, DefaultMutableTreeNode padre) throws RemoteException {
        //sincroniza el servidor con la vista en el cliente
        TreePath camino = find((DefaultMutableTreeNode)directorio.getRoot(),padre);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode)camino.getLastPathComponent());
        int servidor = 1;
        selectedNode.add(new DefaultMutableTreeNode(new NodoArbol(nombre,servidor,'d')));
        System.out.println(nombre);
        new File("directorio.bin").delete();
        File archivo = null;
        try{
            archivo = new File ("directorio.bin");            
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(directorio);
            objeto.flush();
            objeto.close();
            salida.close();
            
        }catch(Exception ex3){
        
        }
        //codigo para guardar en el data node
        return true;
    }

    @Override
    public boolean crearArchivo(String nombre, String Text, DefaultMutableTreeNode padre) throws RemoteException {
        //sincroniza el servidor con la vista en el cliente
        TreePath camino = find((DefaultMutableTreeNode)directorio.getRoot(),padre);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode)camino.getLastPathComponent());
        int servidor = 1;
        selectedNode.add(new DefaultMutableTreeNode(new NodoArbol(nombre,servidor,'a')));
        new File("directorio.bin").delete();
        File archivo = null;
        try{
            archivo = new File ("directorio.bin");            
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(directorio);
            objeto.flush();
            objeto.close();
            salida.close();
            
        }catch(Exception ex3){
        
        }
        //codigo para guardar en el data node
        
       return true;
    }

    @Override
    public boolean borrarArchivo(String nombre, DefaultMutableTreeNode nodo) throws RemoteException {
        TreePath camino = find((DefaultMutableTreeNode)directorio.getRoot(),nodo);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode)camino.getLastPathComponent());
        ((DefaultMutableTreeNode)selectedNode.getParent()).remove(selectedNode);
        new File("directorio.bin").delete();
        File archivo = null;
        try{
            archivo = new File ("directorio.bin");            
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(directorio);
            objeto.flush();
            objeto.close();
            salida.close();
            
        }catch(Exception ex3){
        
        }
        //codigo para eliminar en el dataserver
       return true;
    }

    @Override
    public boolean borrarDirectorio(String nombre, DefaultMutableTreeNode nodo) throws RemoteException {
        TreePath camino = find((DefaultMutableTreeNode)directorio.getRoot(),nodo);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode)camino.getLastPathComponent());
        ((DefaultMutableTreeNode)selectedNode.getParent()).remove(selectedNode);
        new File("directorio.bin").delete();
        File archivo = null;
        try{
            archivo = new File ("directorio.bin");            
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(directorio);
            objeto.flush();
            objeto.close();
            salida.close();
            
        }catch(Exception ex3){
        
        }
        //codigo para eliminar en el dataserver
        
       return true;
    }

    @Override
    public String verArchivo(String nombre) throws RemoteException {
       return "";
    }
    
    @Override
    public DefaultTreeModel getEstructura() throws RemoteException {
        return directorio;
    }
     private static void iniciar(){
        
       String host = "169.254.235.211";//"192.168.56.1"
        try {
            // create on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServerNode", new ServerNodeImpl()); 
            System.out.println("ServerNode iniciado"); 
            
            
            Registry reg2 = LocateRegistry.getRegistry(host, 1100); // agregar host
            dataserver1 = (DataServer) reg2.lookup("DataServer1");
            System.out.println("Data server 1 conectado");

            Registry reg3 = LocateRegistry.getRegistry(host,1101 );
            replicadataserver1 = (DataServer) reg3.lookup("ReplicaDataServer1");
            System.out.println("Replica Data server 1 conectado");

            Registry reg4 = LocateRegistry.getRegistry(host,1102);
            dataserver2 = (DataServer) reg4.lookup("DataServer2");
            System.out.println("Data server 2 conectado");
            
            Registry reg5 = LocateRegistry.getRegistry(host,1103);
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
