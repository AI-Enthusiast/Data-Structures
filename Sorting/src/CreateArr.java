import java.io.IOException;

/**
 * Created by Cormac on 4/20/2017.
 */
public class CreateArr {
    public static void main(String[] arr){
        try {
            Reporting2.printSortedArray("Input_File.txt", Reporting1.generateArray(false,false,25));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
