package pt.atec.network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientModule {

    public final static int SOCKET = 21, FILE_SIZE = 6022386;
    public final static String SERVER = "192.168.250.158";
    
    public String FILE_TO_RECEIVED;
    
    Scanner s = new Scanner(System.in);
    String numFicheiro, lsinput;
    
    PrintWriter out, outfile;
    BufferedReader in, infile;
    String inLine, outLine;
    Socket sock;

    public ClientModule() throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos1 = null, fos2 = null;
        BufferedOutputStream bos1 = null, bos2 = null;
        try {
            sock = new Socket(SERVER, SOCKET);
            
            System.out.println("Conectado!");
            
            //Receber o array de ficheiros ***********************
            lsinput = new String();
            lsinput = s.nextLine();
            
            if(lsinput == "ls"){
                byte[] bytearray1 = new byte[FILE_SIZE];
                InputStream is1 = sock.getInputStream();
                fos1 = new FileOutputStream("C:/arrayfile.txt");
                bos1 = new BufferedOutputStream(fos1);
                bytesRead = is1.read(bytearray1, 0, bytearray1.length);
                current = bytesRead;

                do {
                    bytesRead = is1.read(bytearray1, current, (bytearray1.length - current));
                    if (bytesRead >= 0) {
                        current += bytesRead;
                    }
                } while (bytesRead > -1);

                bos1.write(bytearray1, 0, current);
                bos1.flush();
                System.out.println("Array recebido.");


                System.out.println("Conteudo do server:");

                infile = new BufferedReader(new FileReader("C:/arrayfile.txt"));

                String line;
                int i = 0;
                ArrayList<String> nomeDosFicheiros = new ArrayList<>();
                while((line = infile.readLine()) != null)
                {
                    System.out.println(i+". "+line);
                    nomeDosFicheiros.add(line);
                    i++;
                }
                infile.close();

                System.out.println("Seleciona o número do ficheiro que quer transferir:");

                numFicheiro = s.nextLine();

                outfile = new PrintWriter(new FileWriter("C:/outputfile.txt"));
                outfile.print(numFicheiro); 
                outfile.close();

                for(int n = 0; n < nomeDosFicheiros.size(); n++){
                    if(n == Integer.parseInt(numFicheiro)){
                        FILE_TO_RECEIVED = "C:/Users/Henrique/Downloads/"+nomeDosFicheiros.get(n);
                    }
                }

                // receber o ficheiro ***********************
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
                System.out.println("Ficheiro " + FILE_TO_RECEIVED + " transferido (" + current + " bytes lidos)");
            }
        } finally {
            if (fos2 != null && fos1 != null) {
                fos2.close();
                fos1.close();
            }
            if (bos2 != null && bos1 != null) {
                bos2.close();
                bos1.close();
            }
            if (sock != null) {
                sock.close();
            }
        }
    }

}
