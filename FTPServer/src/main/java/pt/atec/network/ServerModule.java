package pt.atec.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public final class ServerModule {

    public final static int SOCKET = 21;
    int numero = 0, i = 0;
    String inputLine;

    OutputStream osi;
    InputStream isi;
    
    PrintWriter out;
    BufferedReader in;

    byte[] bs;

    public String FILE_TO_SEND;

    public ServerModule() throws IOException {
        FileInputStream fis = null, fis2;
        BufferedInputStream bis = null, bis2;
        OutputStream os = null, os2;
        ServerSocket servsock = null;
        Socket sock = null;

        try {
            servsock = new ServerSocket(SOCKET);
            while (true) {
                System.out.println("Em espera...");
                try {
                    sock = servsock.accept();
                    System.out.println("Conecção aceite : " + sock + "\n");

                    //Enviar o array de ficheiros
                    
                    osi = sock.getOutputStream();
                    isi = sock.getInputStream();
                    
                    File pasta = new File("C:/Java/");
                    File[] listaDeFicheiros = pasta.listFiles();

                    for (File file : listaDeFicheiros) {
                        if (file.isFile()) {
                            bs = file.getName().getBytes();
                            i++;
                        }
                    }
                    osi.write(bs);

                    /*byte bin = 0;
                    ArrayList<Byte> arb = new ArrayList<Byte>();

                    while (bin >= 0){
                        bin = (byte) isi.read();
                        arb.add(bin);
                    }

                   byte[] b = new byte[arb.size()];

                   for(int i = 0; i < arb.size()-1; i++){
                       b[i] = arb.get(i);
                   }*/

/******************************************************************************/
                    
                    inputLine = in.readLine(); //não tenho a certeza

                    for (File file : listaDeFicheiros) {
                        if (file.toString() == inputLine) {
                            FILE_TO_SEND = "C:/Java/"+file.toString();
                        }
                    }

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
                    if (bis != null) {
                        bis.close();
                    }
                    if (os != null) {
                        os.close();
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
