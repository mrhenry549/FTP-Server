/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.atec.network;

import java.io.DataInputStream;
import java.net.Socket;

public class ClienteNome {
    private final int PORTO = 80;
    private final String IP = "192.168.250.250";
    public String msgin = "";

    public ClienteNome() {
        try {

            Socket sock = new Socket(IP, PORTO);

            DataInputStream din = new DataInputStream(sock.getInputStream());

            msgin = din.readUTF(); 

        } catch (Exception e) {

        }
    }
}