import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Idccapp22 on 2016/12/30.
 */
public class test {
    public static void main(String[] args) {

//        String name = test.toGBK("\u6DF1\u5733\u5E02\u536F\u4E01\u6280\u672F\u6709\u9650\u516C\u53F8");
//        System.out.print("\u6DF1\u5733\u5E02\u536F\u4E01\u6280\u672F\u6709\u9650\u516C\u53F8");


//        String a="a;;b; ; ;";
//        String[] b = a.split(";");
//        System.out.print(b);

        BigDecimal d = new BigDecimal("2.0030000");
        System.out.println(getRealData(d));

        System.out.println(d.stripTrailingZeros());

        String s = "group1/M00/00/52/rBAGSVjsSMmAe8ZXAAAySlSj4Gk875.jpg";
        System.out.println(s.substring(0,6));
        System.out.println(s.substring(7));
    }

    public static String toGBK(String unicodeStr) {
        try {
            String gbkStr = new String(unicodeStr.getBytes("ISO8859-1"), "UTF-8");
            return gbkStr;
        } catch (UnsupportedEncodingException e) {
            return unicodeStr;
        }
    }

    private class TimeData{
        String startTime;
        String endTime;

        public TimeData(String startTime,String endTime){
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public TimeData(){

        }
        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    private class TimeCalculationResult implements Comparable<TimeCalculationResult>, Comparator<TimeCalculationResult> {
        private int level;//按照等级排序（升序排列）
        private int differenceDay;//（如果等级一样，按照differenceDay排序）

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getDifferenceDay() {
            return differenceDay;
        }

        public void setDifferenceDay(int differenceDay) {
            this.differenceDay = differenceDay;
        }

        @Override
        public int compareTo(TimeCalculationResult o) {
            int flag = this.getLevel() - o.getLevel();
            if(flag ==0){
                return this.getDifferenceDay() - o.getDifferenceDay();
            }
            return flag;
        }


        @Override
        public int compare(TimeCalculationResult o1, TimeCalculationResult o2) {
            int flag = o1.getLevel() - o2.getLevel();
            if(flag ==0){
                return o1.getDifferenceDay() - o2.getDifferenceDay();
            }
            return flag;
        }
    }

    public  List<TimeData> timeOrderByStart(){
        String startTime = "2017-02-03";
        String endTime = "2017-02-13";
        List<TimeData> list1 = new ArrayList<TimeData>();
        list1.add(new TimeData("2017-02-01","2017-02-11"));
        list1.add(new TimeData("2017-02-05","2017-03-11"));

        List<TimeData> list2 = new ArrayList<TimeData>();
        list2.add(new TimeData("2017-02-06","2017-02-11"));
        list2.add(new TimeData("2017-03-01","2017-03-11"));
        list2.add(new TimeData("2017-03-05","2017-03-11"));

        List<TimeData> list3 = new ArrayList<TimeData>();
        list2.add(new TimeData("2017-02-06","2017-02-11"));

        List<TimeData> list4 = new ArrayList<TimeData>();


        return list1;
    }

    public void timeOrderMethod(String startTime,String endTime,List<TimeData> list){
       // List<>

        for(TimeData data:list){

        }
    }


    private static String getRealData(BigDecimal num) {
        if (num == null) {
            return "0";
        }
        String value = num.stripTrailingZeros().toString();
        return value;
    }
}
