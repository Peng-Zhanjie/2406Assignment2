package Model;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenFile {
    Path file= Paths.get("SaveFile.txt");
    public byte[] getItem(){
        try {
              InputStream input= new BufferedInputStream(Files.newInputStream(file));
              byte data[] = new byte[1024];
              input.read(data);
              System.out.print("Open Finished\n");
              return data;
        } catch (Exception e) {
              System.out.println("Message: " + e);
              return new byte[1024];
        }
    }

}
