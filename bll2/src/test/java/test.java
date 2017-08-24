import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by Idccapp22 on 2016/7/20.
 */
public class test {

    public static void  main(String args[]){
            int [] data = {1,2,3,4,5,6,7,8,9};
            int [] newData;
            newData = Arrays.copyOfRange(data, 0, data.length-1);
            for(int i:newData)
                System.out.print(i+" ");

        StringBuffer ids= new StringBuffer();
        String id = ids.toString();
        System.out.println("id:"+ids);

    }

    public  String hello(){
        String res = "3";
        int i =1;
        while (true){
            if(i==5){
               /* res = "2";
                break;*/
                return res;
            }
            i++;
            System.out.println(i);
        }

    }


}
