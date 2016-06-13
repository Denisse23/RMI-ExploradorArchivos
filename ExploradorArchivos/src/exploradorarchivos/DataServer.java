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
public interface DataServer extends Remote {
    public void Mensaje(String mensaje)throws RemoteException;
}
