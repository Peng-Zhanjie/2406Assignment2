package Model;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;

public class SaveFile {
    Path file = Paths.get("SaveFile.txt");
    public void saveItem(String text) {
        String s = text;
        byte[] data = s.getBytes();
        OutputStream output;
        try {
            output = new BufferedOutputStream(Files.newOutputStream(file));
            output.write(data);
            output.flush();
            output.close();
            System.out.print("Save Finished\n");
        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists");
        } catch (Exception e) {
            System.out.println("Message: " + e);
        }
    }
}
