/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.atec.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerCom {

    private final int PORTO = 80;

    public ServerCom() {
        
        ClienteArray ca = new ClienteArray();
        
        System.out.println(ca.msgin);
        System.out.println("Escolha o ficheiro que quer transferir");
        
        Scanner s = new Scanner(System.in);
        
        int ficheiro = s.nextInt();

        try {
            ServerSocket ssoc = new ServerSocket(PORTO);
            Socket sock = ssoc.accept();

            DataOutputStream dout = new DataOutputStream(sock.getOutputStream());

            dout.write(ficheiro);
            dout.flush();

        } catch (Exception e) {

        }

    }

}