import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Cormac
 */
public class Reporting2 {
    public static void main (String[] args){
        String fileName = "";
        /* convert name to one String */
        for (int index = 0; index < args.length - 1; index++){
            if (fileName.charAt(index) != '<' && fileName.charAt(index) != '>') {
                fileName = fileName + args[index];
            }
        }
        /* find file */
        try {
            /* retrieve the contents of the file */
            byte[] contents = Files.readAllBytes(Paths.get(fileName));
            /* convert contents to an array you can use */
            int[] arr = new int[contents.length - 1];
            for (int index = 0; index < arr.length - 1; index++){
                arr[index] = contents[index];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
