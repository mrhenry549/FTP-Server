package pt.atec.network;

import java.io.*;

import java.net.*;

public class ClientModule {

    private final int PORTO = 9983;
    private final String IP = "192.168.250.250";

    public ClientModule() throws IOException {

        Socket sock = new Socket(IP, PORTO);

        DataInputStream din = new DataInputStream(sock.getInputStream());
        DataOutputStream dout = new DataOutputStream(sock.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String msgin = "", msgout = "";

        byte[] bytearray = new byte[6022386];

        InputStream is = sock.getInputStream();

        try {

            msgout = br.readLine();
            dout.writeUTF(msgout);
            System.out.println("Introduza o numero do ficheiro");
            msgin = din.readUTF();
            System.out.println(msgin);
            FileOutputStream fos = new FileOutputStream("C:/Users/Henrique/Downloads");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesread = is.read(bytearray, 0, bytearray.length);
            bos.write(bytearray, 0, bytesread);
            System.out.println("O ficheiro foi recebido com sucesso!");
            bos.close();
            sock.close();

        } catch (Exception SVI) {

            System.out.print(SVI);

        }

    }

}
