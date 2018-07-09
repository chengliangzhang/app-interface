import com.maoding.project.entity.ProjectEntity;

import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by Idccapp22 on 2016/7/20.
 */
public class test {

    private ProjectEntity projectEntity = new ProjectEntity();
    public static void  main(String args[]){
            int [] data = {1,2,3,4,5,6,7,8,9};
            int [] newData;
            newData = Arrays.copyOfRange(data, 0, data.length-1);
            for(int i:newData)
                System.out.print(i+" ");

        StringBuffer ids= new StringBuffer();
        String id = ids.toString();
        System.out.println("id:"+ids);

        test t = new test();
        ProjectEntity p= t.getProjectEntity();
        System.out.print(p);
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

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }


}
