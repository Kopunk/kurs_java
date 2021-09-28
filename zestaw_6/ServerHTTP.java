import java.io.*;
import java.net.*;

public class ServerHTTP {
   final static String htmlFileName = "index.html";

   public static void main(String[] args) throws IOException {
      ServerSocket serv = new ServerSocket(8080);

      while (true) {
         // przyjecie polaczenia
         System.out.println("Oczekiwanie na polaczenie...");
         Socket sock = serv.accept();

         // strumienie danych
         InputStream is = sock.getInputStream();
         OutputStream os = sock.getOutputStream();
         BufferedReader inp = new BufferedReader(new InputStreamReader(is));
         DataOutputStream outp = new DataOutputStream(os);

         // przyjecie zadania (request)
         String request = inp.readLine();
         System.out.println(request);
         do {
            System.out.println(inp.readLine());
         } while (inp.ready());

         System.out.println("odp:");

         // wyslanie odpowiedzi (response)
         if (request.startsWith("GET")) {
            try {
               // https://gist.github.com/oliveratgithub/e60d92706218812b6630045f59191f3d
               FileInputStream fis = new FileInputStream(htmlFileName);

               // response header
               outp.writeBytes("HTTP/1.0 200 OK\r\n");

               System.out.println(fis.toString());

               String contentType = (htmlFileName.endsWith(".html") || htmlFileName.endsWith(".htm")) ? "text/html"
                     : "";
               outp.writeBytes("Content-Type: " + contentType + "\r\n");
               outp.writeBytes("Content-Length: " + fis.available() + "\r\n");
               outp.writeBytes("\r\n");

               // response
               byte[] bufor;
               bufor = new byte[1024];
               int n = 0;

               while ((n = fis.read(bufor)) != -1) {
                  outp.write(bufor, 0, n);
               }

               fis.close();
            } catch (Exception e) {
               outp.writeBytes("HTTP/1.0 404 Not Found\r\n");
            }

         } else {
            outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");
         }

         System.out.println("przegladarka zada: " + request.split(" ")[1]);

         // zamykanie strumieni
         inp.close();
         outp.close();
         sock.close();
      }
   }
}