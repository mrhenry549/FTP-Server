package pt.atec.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public final class ServerModule {

    public final static int SOCKET = 21;
    int numero = 0, i = 0;
    String inputLine;

    PrintWriter out, outfile;
    BufferedReader in;

    public String FILE_TO_SEND, LOG_FILE = "C:/ftp/logs.txt";

    public ServerModule() throws IOException {
        int bytesRead;
        int current = 0;
        FileInputStream fis1 = null, fis2 = null;
        FileOutputStream fos1 = null, fos2 = null;
        BufferedInputStream bis1 = null, bis2 = null;
        BufferedOutputStream bos1 = null, bos2 = null;
        OutputStream os1 = null, os2 = null;
        InputStream is1 = null, is2 = null;
        ServerSocket servsock = null;
        Socket sock = null;

        try {
            servsock = new ServerSocket(SOCKET);
            while (true) {
                System.out.println("Em espera...");
                try {
                    sock = servsock.accept();
                    System.out.println("Conecção aceite : " + sock + "\n");
                    
                    //Listagem de ficheiros
                    File pasta = new File("C:/ftp/");
                    File[] listaDeFicheiros = pasta.listFiles();
                    
                    //conversão do array em ficheiro
                    
                    outfile = new PrintWriter(new FileWriter("C:/ftp/outputfile.txt"));
                    
                    if (listaDeFicheiros.length > 0) {
                        StringBuilder sb = new StringBuilder();

                        for (File file : listaDeFicheiros) {
                            if (file.isFile()) {
                                outfile.print(listaDeFicheiros[i]);
                            }
                        }
                        outfile.close();
                    }
                    
                    File arrayFile = new File("C:/ftp/outputfile.txt");
                    byte[] bytearray = new byte[(int) arrayFile.length()];
                    fis1 = new FileInputStream(arrayFile);
                    bis1 = new BufferedInputStream(fis1);
                    bis1.read(bytearray, 0, bytearray.length);
                    os1 = sock.getOutputStream();
                    System.out.println("A enviar o array...");
                    os1.write(bytearray, 0, bytearray.length);
                    os1.flush();
                    System.out.println("Feito.");
                    System.out.println("À espera do cliente...");
                    
                    //recebe o commando
                    
                    byte[] bytearray2 = new byte[1024];
                    is2 = sock.getInputStream();
                    fos2 = new FileOutputStream("C:/log.txt");
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
                    System.out.println("Comando recebido");
                    
                    String line;
                    while((line = in.readLine()) != null)
                    {
                        int num = Integer.parseInt(line);
                        FILE_TO_SEND = "C:/ftp/"+listaDeFicheiros[num];
                    }
                    in.close();

                    // enviar o ficheiro
                    File myFile = new File(FILE_TO_SEND);
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    fis2 = new FileInputStream(myFile);
                    bis2 = new BufferedInputStream(fis2);
                    bis2.read(mybytearray, 0, mybytearray.length);
                    os2 = sock.getOutputStream();
                    System.out.println("A enviar " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                    os2.write(mybytearray, 0, mybytearray.length);
                    os2.flush();
                    System.out.println("Feito.");

                } finally {
                    if (bis1 != null && bis2 != null) {
                        bis1.close();
                        bis2.close();
                    }
                    if (os1 != null && os2 != null) {
                        os1.close();
                        os2.close();
                    }
                    if (sock != null) {
                        sock.close();
                    }
                }
            }
        } finally {
            if (servsock != null) {
                servsock.close();
            }
        }
        
        
    }
}
