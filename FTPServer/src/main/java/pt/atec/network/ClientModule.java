package pt.atec.network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientModule {

    public final static int SOCKET = 21;
    public final static String SERVER = "192.168.250.231";

    public final static int FILE_SIZE = 6022386;
    
    public String FILE_TO_RECEIVED;
    Scanner s = new Scanner(System.in);
    String nomeFicheiro;
    
    int i;
    PrintWriter out;
    BufferedReader in;
    String inLine;
        
    File pasta = new File("C:/Java/");
    File[] listaDeFicheiros = pasta.listFiles();

    public ClientModule() throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null, fos2 = null;
        BufferedOutputStream bos = null, bos2 = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET);
            
            System.out.println("conectando...");
            
            //Receber o array de ficheiros
            
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream())); 
            
            inLine = in.readLine();
            
            System.out.println("Conteudo do server:");
            System.out.println(inLine);
            System.out.println("Seleciona o nome do ficheiro que quer transferir:");
            
            nomeFicheiro = s.nextLine();
            
            out.println(nomeFicheiro); 

            FILE_TO_RECEIVED = "C:/Users/Henrique/Downloads/"+nomeFicheiro;
            
            // receber o ficheiro
            byte[] bytearray2 = new byte[FILE_SIZE];
            InputStream is2 = sock.getInputStream();
            fos2 = new FileOutputStream(FILE_TO_RECEIVED);
            bos2 = new BufferedOutputStream(fos2);
            bytesRead = is2.read(bytearray2, 0, bytearray2.length);
            current = bytesRead;

            do {
                bytesRead = is2.read(bytearray2, current, (bytearray2.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);

            bos2.write(bytearray2, 0, current);
            bos2.flush();
            System.out.println("Ficheiro " + FILE_TO_RECEIVED
                    + " transferido (" + current + " bytes lidos)");
        } finally {
            if (fos2 != null) {
                fos2.close();
            }
            if (bos2 != null) {
                bos2.close();
            }
            if (sock != null) {
                sock.close();
            }
        }
    }

}
