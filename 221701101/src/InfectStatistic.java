import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InfectStatistic
 * TODO
 *
 * @author linlu
 * @version 1.0
 * @since 2020/2/11
 */
class InfectStatistic {
    static class Parameters {
        String log;
        String out;
        String date;
        ArrayList<String> type = null;
        ArrayList<String> province = null;
    }

    static class Regular{
        String regularOne = "\\W+ 新增 感染患者 \\d+人";
        String regularTwo = "\\W+ 新增 疑似患者 \\d+人";
        String regularThree = "\\W+ 感染患者 流入 \\W+ \\d+人";
        String regularFour = "\\W+ 疑似患者 流入 \\W+ \\d+人";
        String regularFive = "\\W+ 死亡 \\d+人";
        String regularSix = "\\W+ 治愈 \\d+人";
        String regularSeven = "\\W+ 疑似患者 确诊感染 \\d+人";
        String regularEight = "\\W+ 排除 疑似患者 \\d+人";
    }

    static class Result{
        String province;
        int ip;
        int sp;
        int cure;
        int dead;
        public Result(){
            this.province = null;
            this.ip = 0;
            this.sp = 0;
            this.cure = 0;
            this.dead = 0;
        }
        public void setProvince(String province){
            this.province = province;
        }
        public String getProvince(){
            return province;
        }
        public void setIp(int ip){
            this.ip = ip;
        }
        public int getIp(){
            return ip;
        }
        public void setSp(int sp){
            this.sp = sp;
        }
        public int getSp(){
            return sp;
        }
        public void setCure(int cure){
            this.cure = cure;
        }
        public int getCure(){
            return cure;
        }
        public void setDead(int dead){
            this.dead = dead;
        }
        public int getDead(){
            return dead;
        }

        public String getResultString() {
            return province + " " + "感染患者" + ip + "人" + " " + "疑似患者" + sp + "人" + " " +"治愈" + cure + "人" + " " + "死亡" + dead + "人";
        }
    }
    /**
     *@描述  main函数
     *@参数  String[] args
     *@返回值  void
     *@创建人  221701101林露
     *@创建时间  2020/2/12
     */
    public static void main(String[] args) {
        List<Result> resultList = new ArrayList<Result>();
        list(args);
        Parameters param=ParseOptions(args);

    }

    /**
     *@描述  根据文件名读取相关日志文件，每次读取一行，将该行转化为Result类，最后返回Result集合
     *@参数  String fileName,String date
     *@返回值  List<Result>
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public  static List<Result> initLog(String fileName){// ,String date
        Regular regular = new Regular();
        List<Result> list = new ArrayList<Result>();
        File file = new File(fileName);
        if(file.isFile()){
            BufferedReader reader = null;
            try{
                reader = new BufferedReader(new FileReader(file));
                String currentLine = null;
                while((currentLine = reader.readLine()) != null){
                   // 1、<省> 新增 感染患者 n人
                    if(currentLine.matches(regular.regularOne)){
                        Result result = getIpResult(currentLine);
                        list.add(result);
                    }
                   // 2、<省> 新增 疑似患者 n人
                    else if(currentLine.matches(regular.regularTwo)){
                        Result result = getSpResult(currentLine);
                        list.add(result);
                    }
                    //3、<省1> 感染患者 流入 <省2> n人
                    else if(currentLine.matches(regular.regularThree)){
                        List<Result> resultList = getIpMoveResult(currentLine);
                        for(int i = 0;i < resultList.size();i++)
                            list.add(resultList.get(i));
                    }
                    //4、<省1> 疑似患者 流入 <省2> n人
                    else if(currentLine.matches(regular.regularFour)){
                        List<Result> resultList = getIpMoveResult(currentLine);
                        for(int i = 0;i < resultList.size();i++)
                            list.add(resultList.get(i));
                    }
                    //5、<省> 死亡 n人
                    else if(currentLine.matches(regular.regularFive)){
                        Result result = getDeadResult(currentLine);
                        list.add(result);
                    }
                    //6、<省> 治愈 n人
                    else if(currentLine.matches(regular.regularSix)){
                        Result result = getCureResult(currentLine);
                        list.add(result);
                    }
                    //7、<省> 疑似患者 确诊感染 n人
                    else if(currentLine.matches(regular.regularSeven)){
                        Result result = getSpToIpResult(currentLine);
                        list.add(result);
                    }
                    //8、<省> 排除 疑似患者 n人
                    else if(currentLine.matches(regular.regularEight)){
                        Result result = getSpClearResult(currentLine);
                        list.add(result);
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     *@描述  当前行为 “<省> 新增 感染患者 n人”对应语句，将其转换为Result类，获得某省感染患者变化结果
     *@参数  String currentLine 表示当前行
     *@返回值  Reesult
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static Result getIpResult(String currentLine){
        Result result = new Result();
        Pattern pattern1 = Pattern.compile("(.*) 新增");
        Pattern pattern2 = Pattern.compile("感染患者 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()){
            result.setProvince(matcher1.group(1));
        }
        if(matcher2.find()){
            result.setIp(Integer.parseInt(matcher2.group(1)));
        }
        return result;
    }
    /**
     *@描述  当前行为 “<省> 新增 疑似患者 n人”对应语句，将其转换为Result类，获得某省疑似患者变化结果
     *@参数  String currentLine 表示当前行
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static Result getSpResult(String currentLine){
        Result result = new Result();
        Pattern pattern1 = Pattern.compile("(.*) 新增");
        Pattern pattern2 = Pattern.compile("疑似患者 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()){
            result.setProvince(matcher1.group(1));
        }
        if(matcher2.find()){
            result.setSp(Integer.parseInt(matcher2.group(1)));
        }
        return result;
    }
    /**
     *@描述  当前行为 “<省> 治愈 n人”对应语句，将其转换为Result类，获得某省治愈患者变化结果
     *@参数  String currentLine 当前行
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static Result getCureResult(String currentLine){
        Result result = new Result();
        Pattern pattern1 = Pattern.compile("(.*) 治愈");
        Pattern pattern2 = Pattern.compile("治愈 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()){
            result.setProvince(matcher1.group(1));
        }
        if(matcher2.find()){
            result.setIp(-Integer.parseInt(matcher2.group(1)));
            result.setCure(Integer.parseInt(matcher2.group(1)));
        }
        return result;
    }
    /**
     *@描述  当前行为 “<省> 死亡 n人”对应语句，将其转换为Result类，获得某省死亡患者变化结果
     *@参数  String currentLine
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static Result getDeadResult(String currentLine){
        Result result = new Result();
        Pattern pattern1 = Pattern.compile("(.*) 死亡");
        Pattern pattern2 = Pattern.compile("死亡 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()){
            result.setProvince(matcher1.group(1));
        }
        if(matcher2.find()){
            result.setIp(-Integer.parseInt(matcher2.group(1)));
            result.setDead(Integer.parseInt(matcher2.group(1)));
        }
        return result;
    }
    /**
     *@描述 当前行为 “<省1> 感染患者 流入 <省2> n人”对应语句，将其转换为Result类，获得两省感染患者变化结果
     *@参数 String currentLine 当前行
     *@返回值  List<Result>
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static List<Result> getIpMoveResult(String currentLine){
        List<Result> resultList = new ArrayList<Result>();
        Pattern pattern1 = Pattern.compile("(.*) 感染患者");
        Pattern pattern2 = Pattern.compile("流入 (.*) \\d+人");
        Pattern pattern3 = Pattern.compile("\\W+ (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        Matcher matcher3 = pattern3.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            resultList.get(0).setProvince(matcher1.group(1));
            resultList.get(1).setProvince(matcher2.group(1));
        }
        if(matcher3.find()){
            resultList.get(0).setIp(-Integer.parseInt(matcher3.group(1)));
            resultList.get(1).setIp(Integer.parseInt(matcher3.group(1)));
        }
        return resultList;
    }
    /**
     *@描述 当前行为 “<省1> 疑似患者 流入 <省2> n人”对应语句，将其转换为Result类，获得两省疑似患者变化结果
     *@参数  String currentLine
     *@返回值  List<Result>
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static List<Result> getSpMoveResult(String currentLine){
        List<Result> resultList = new ArrayList<Result>();
        Pattern pattern1 = Pattern.compile("(.*) 疑似患者");
        Pattern pattern2 = Pattern.compile("流入 (.*) \\d+人");
        Pattern pattern3 = Pattern.compile("\\W+ (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        Matcher matcher3 = pattern3.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            resultList.get(0).setProvince(matcher1.group(1));
            resultList.get(1).setProvince(matcher2.group(1));
        }
        if(matcher3.find()){
            resultList.get(0).setSp(-Integer.parseInt(matcher3.group(1)));
            resultList.get(1).setSp(Integer.parseInt(matcher3.group(1)));
        }
        return resultList;
    }
    /**
     *@描述 当前行为 “<省> 疑似患者 确诊感染 n人”对应语句，将其转换为Result类，获得某省疑似患者、感染患者变化结果
     *@参数  String currentLine
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static Result getSpToIpResult(String currentLine){
        Result result = new Result();
        Pattern pattern1 = Pattern.compile("(.*) 疑似患者");
        Pattern pattern2 = Pattern.compile("确诊感染 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()){
            result.setProvince(matcher1.group(1));
        }
        if(matcher2.find()){
            result.setIp(Integer.parseInt(matcher2.group(1)));
            result.setSp(-Integer.parseInt(matcher2.group(1)));
        }
        return result;
    }
    /**
     *@描述  当前行为 “<省> 排除 疑似患者 n人”对应语句，将其转换为Result类，获得某省疑似患者变化结果
     *@参数  String currentLine
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public static Result getSpClearResult(String currentLine){
        Result result = new Result();
        Pattern pattern1 = Pattern.compile("(.*) 排除");
        Pattern pattern2 = Pattern.compile("疑似患者 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()){
            result.setProvince(matcher1.group(1));
        }
        if(matcher2.find()){
            result.setSp(-Integer.parseInt(matcher2.group(1)));
        }
        return result;
    }

    /**
     *@描述 测试输出
     *@参数  String[] args
     *@返回值  void
     *@创建人  221701101林露
     *@创建时间  2020/2/12
     */
    public static void list(String[] args){
        Parameters param=ParseOptions(args);
        System.out.println(param.log);
        System.out.println(param.out);
        System.out.println(param.date);
        try {
            if (!param.type.isEmpty()) {
                for (int i = 0; i < param.type.size(); i++)
                    System.out.println(param.type.get(i));
            }
            if (!param.province.isEmpty()) {
                for (int i = 0; i < param.province.size(); i++)
                    System.out.println(param.province.get(i));
            }
        }catch (NullPointerException e){
            System.out.println("Catch NullPointerException");
        }
    }

    /**
     *@描述 获得所有日志文件
     *@参数  rootPath
     *@返回值  List<String>
     *@创建人  221701101林露
     *@创建时间  2020/2/12
     */
    public static List<String> getLogFiles(String rootPath){
        List<String> logFiles = new ArrayList<String>();
        File file = new File(rootPath);
        File[] files = file.listFiles();
        for(int i = 0;i < files.length;i++){
            if(files[i].isFile()){
                logFiles.add(files[i].toString());
            }
        }
        return logFiles;
    }

    public static Parameters ParseOptions(String[] args) {
        Parameters parameters = new Parameters();
        ArrayList<String> typeList = new ArrayList<>();
        ArrayList<String> provinceList = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-log")) {
                parameters.log = args[i + 1];
                i++;
            } else if (args[i].equals("-out")) {
                parameters.out = args[i + 1];
                i++;
            } else if (args[i].equals("-date")) {
                if(i == args.length-1)
                    parameters.date = "default";
                else{
                    if(args[i + 1].substring(0,1).equals("-"))
                        parameters.date="default";
                    else {
                        parameters.date = args[i + 1];
                        i++;
                    }
                }
            } else if (args[i].equals("-type")) {
                for (int j = ++i ; j < args.length; j++,i++) {
                    if (!args[j].substring(0, 1).equals("-")) {
                        typeList.add(args[j]);
                    }
                }
            } else if (args[i].equals("-province")) {
                for (int j = ++i; j < args.length; j++,i++) {
                    if (!args[j].substring(0, 1).equals("-")) {
                        provinceList.add(args[j]);
                    }
                }
            }
        }
        parameters.type=typeList;
        parameters.province=provinceList;
        return parameters;
    }
}
