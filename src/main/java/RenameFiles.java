import java.io.File;
import java.io.IOException;

public class RenameFiles {
    public static void renameFiles() {

        File folder = new File("\\Projects\\sample");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {

                File f = new File("c:\\Projects\\sample\\"+listOfFiles[i].getName());

                f.renameTo(new File("c:\\Projects\\sample\\"+i+".txt"));
            }
        }

        System.out.println("conversion is done");
    }
}
