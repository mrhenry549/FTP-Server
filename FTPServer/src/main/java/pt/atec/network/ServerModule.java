package pt.atec.network;

import java.io.*;

import java.net.*;

public final class ServerModule {

    private final int PORTO = 9983;

    public ServerModule() throws IOException {

        try {

            ServerSocket ssoc = new ServerSocket(PORTO);
            Socket sock = ssoc.accept();

            DataInputStream din = new DataInputStream(sock.getInputStream());
            DataOutputStream dout = new DataOutputStream(sock.getOutputStream());

            String msgin = "", msgout = "";

            File pasta = new File("C:/ftp/");
            File[] listaDeFicheiros = pasta.listFiles();

            String result;
            int i = 0;

            while (true) {
                if (listaDeFicheiros.length > 0) {
                    StringBuilder sb = new StringBuilder();

                    for (File file : listaDeFicheiros) {
                        if (file.isFile()) {
                            sb.append(i).append(file).append(",");
                            i++;
                        }
                    }

                    msgout = sb.deleteCharAt(sb.length() - 1).toString();
                }

                dout.writeUTF(msgout);
                dout.flush();
                msgin = din.readUTF();
                System.out.println(msgin);
                String fil = "";

                for (File file : listaDeFicheiros) {
                    if (file.toString() == listaDeFicheiros[Integer.parseInt(msgin)].toString()) {
                        fil = file.toString();
                    }
                }

                File myfile = new File(fil);

                byte[] mybytearray = new byte[(int) myfile.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myfile));
                bis.read(mybytearray, 0, mybytearray.length);
                OutputStream os = sock.getOutputStream();
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();
                sock.close();

            }

        } catch (Exception saranvi) {

            System.out.print(saranvi);

        }

    }

}
