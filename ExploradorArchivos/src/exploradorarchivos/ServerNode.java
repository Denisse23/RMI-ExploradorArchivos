/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploradorarchivos;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Denisse Carbajal
 */
public interface ServerNode extends Remote{
    public void mensajeServer(String mensaje)throws RemoteException;
    public boolean crearDirectorio(String nombre)throws RemoteException;
    public boolean crearArchivo(String nombre, String Text)throws RemoteException;
    public boolean borrarArchivo(String nombre) throws RemoteException;
    public boolean borrarDirectorio(String nombre) throws RemoteException;
    public String verArchivo(String nombre)throws RemoteException; 
    
}