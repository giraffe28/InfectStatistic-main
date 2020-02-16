import java.io.*;
import java.util.ArrayList;
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
    public static class Province{
        public static String[] province= {"全国", "安徽", "澳门" ,"北京", "重庆", "福建","甘肃",
                "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林",
                "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海",
                "四川", "台湾", "天津", "西藏", "香港", "新疆", "云南", "浙江"};
        public static String getByIndex(int index){
            return province[index];
        }
    }
    static class ResultList{
        List<Result> resultList = new ArrayList<Result>();
        ResultList(){
             for(int i = 0;i < 35;i++){
                 Result result = new Result();
                 result.setProvince(Province.getByIndex(i));
                 resultList.add(result);
             }
        }
        /**
         *@描述  合并相同省份的数据
         *@参数  List<Result> currentResultList
         *@返回值  List<Result>
         *@创建人  221701101林露
         *@创建时间  2020/2/15
         */
        public  List<Result> mergeList(List<Result> currentResultList,Parameters parameters){
            for (Result currentResult:currentResultList
                 ) {
                for(int i = 0;i < 35;i++ ){
                    if(currentResult.province.equals(resultList.get(i).province)){
                        resultList.get(i).setIp(currentResult.ip + resultList.get(i).ip);
                        resultList.get(i).setSp(currentResult.sp + resultList.get(i).sp);
                        resultList.get(i).setCure(currentResult.cure + resultList.get(i).cure);
                        resultList.get(i).setDead(currentResult.dead + resultList.get(i).dead);
                        resultList.get(i).setRefer(currentResult.isRefer||resultList.get(i).isRefer);
                    }
                }
            }
            if(parameters.province.size() == 0)
                resultList.get(0).setRefer(true);
            else {
                for (String province:parameters.province
                     ) {
                    if(province.equals("全国"))
                        resultList.get(0).setRefer(true);
                }
            }
            return resultList;
        }
    }
    static class Parameters {
        String log;
        String out;
        String date;
        ArrayList<String> type;
        ArrayList<String> province;
        String[] args = null;
        String str1 = null;
        String str2 = null;
        String str3 = null;
        int i = 0;
        //仅为单元测试使用
        public Parameters(){
            log = null;
            out = null;
            date = null;
            type = null;
            province = null;
        }
        public  Parameters(String log,String out,String date,ArrayList<String> type,ArrayList<String> province){
            this.log = log;
            this.out = out;
            this.date = date;
            this.type = type;
            this.province = province;
        }
        public void setArgs(String[] args){
            this.args = args;
        }
        public String getArgsString(){
            String argsStr = "";
            for (String arg:args
                 ) {
                if(!arg.equals(null))
                    argsStr += arg + " ";
            }
            return argsStr;
        }
        public String getParameterString() {
            if(date != null) {
                str1 = "log: " + log + " " + "out: " + out + " " + "date: " + date + " ";
            }
            else{
                 str1 = "log: " + log + " " + "out: " + out + " " + "date: ";
            }
            for(i = 0,str2 = "type: ";i < type.size();i++){
                str2 += type.get(i).toString() + " ";
            }
            for(i = 0,str3 = "province: ";i < province.size();i++){
                str3 += province.get(i).toString() + " ";
            }
            str1+=str2 + str3;
            return str1;
        }
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
        boolean isRefer;
        public Result(){
            this.province = null;
            this.ip = 0;
            this.sp = 0;
            this.cure = 0;
            this.dead = 0;
            this.isRefer = false;
        }
        public void setProvince(String province){
            this.province = province;
        }
        public String getProvince(){
            return province;
        }
        public void setIp(int ip){
            this.ip += ip;
        }
        public int getIp(){
            return ip;
        }
        public void setSp(int sp){
            this.sp += sp;
        }
        public int getSp(){
            return sp;
        }
        public void setCure(int cure){
            this.cure += cure;
        }
        public int getCure(){
            return cure;
        }
        public void setDead(int dead){
            this.dead += dead;
        }
        public int getDead(){
            return dead;
        }
        public void setRefer(boolean bool){
            this.isRefer = bool;
        }

        public String getResultString() {
            return province + " " + "感染患者" + ip + "人" + " " + "疑似患者" + sp + "人" + " " +"治愈" + cure + "人" + " " + "死亡" + dead + "人";
        }
        public String getAssignResultString(Parameters parameters){
            String result = province + " ";
            for (String type:parameters.type
                 ) {
                if(type.equals("ip")){
                    result += " " + "感染患者" + ip + "人";
                }
                else if(type.equals("sp")){
                    result += " " + "疑似患者" + sp + "人";
                }
                else if(type.equals("cure")){
                    result += " " + "治愈" + cure + "人";
                }
                else if(type.equals("dead")){
                    result += " " + "死亡" + dead + "人";
                }
            }
            return result;
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
        if(!args[0].equals("list")) {
            System.out.println("命令行格式有误——应该以list开头");
            return;
        }
        Parameters param = ParseOptions(args);
        if(param.log != null && param.out != null){
            ResultList list = new ResultList();
            List<String> logFiles = getLogFiles(param);
            for (String logfile:logFiles
            ) {
                list.mergeList(parseLog(logfile,list.resultList),param);
            }
            try {
                outPut(list, param);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("log和out参数不能为空，请重新输入！");
        }

    }
/**
 *@描述  输出-out对应文件的内容
 *@参数  String outPath
 *@返回值  void
 *@创建人  221701101林露
 *@创建时间  2020/2/16
 */
    public  static void print(String outPath){
        File file = new File(outPath);
        if(file.isFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String currentLine = null;
                while ((currentLine = reader.readLine()) != null)
                    System.out.println(currentLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *@描述 将所有省份（或指定的省份）的不同类型的（或指定类型的）患者输出到指定文件中
     *@参数  ResultList list,Parameters parameters
     *@返回值   void
     *@创建人  221701101林露
     *@创建时间  2020/2/16
     */
    public static void outPut(ResultList list,Parameters parameters) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(parameters.out)));
        try {
                for (Result result : list.resultList
                ) {
                    //没有指定省份
                    if(parameters.province.size() == 0){
                        list.resultList.get(0).setRefer(true);
                        //在日志中出现
                        if(result.isRefer == true) {
                            //没有指定类型
                            if(parameters.type.size() == 0)
                                bw.write(result.getResultString());
                            //指定类型
                            else
                                bw.write(result.getAssignResultString(parameters));
                            bw.write("\n");
                        }
                }
                    //指定省份
                    else{
                        for (String pro: parameters.province
                        ) {
                            //找到相应省份
                            if(pro.equals(result.province)){
                                //没有指定类型
                                if(parameters.type.size() == 0)
                                    bw.write(result.getResultString());
                                //指定类型
                                else
                                    bw.write(result.getAssignResultString(parameters));

                                bw.write("\n");
                            }
                        }
                    }
            }
            bw.write("// 该文档并非真实数据，仅供测试使用\n");
            bw.write("// 命令：" + parameters.getArgsString() + "\n");
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     *@描述  根据文件名读取相关日志文件，每次读取一行，将该行转化为Result类，最后返回Result集合
     *@参数  String fileName
     *@返回值  List<Result>
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    public  static List<Result> parseLog(String logPath,List<Result> resultList){// ,String date
        Regular regular = new Regular();
        List<Result> list = new ArrayList<Result>();
        File file = new File(logPath);
        if(file.isFile()){
            BufferedReader reader = null;
            try{
                reader = new BufferedReader(new FileReader(file));
                String currentLine = null;
                while((currentLine = reader.readLine()) != null){
                   // 1、<省> 新增 感染患者 n人
                    if(currentLine.matches(regular.regularOne)){
                        getIpResult(currentLine,resultList);
                    }
                   // 2、<省> 新增 疑似患者 n人
                    else if(currentLine.matches(regular.regularTwo)){
                        getSpResult(currentLine,resultList);
                    }
                    //3、<省1> 感染患者 流入 <省2> n人
                    else if(currentLine.matches(regular.regularThree)){
                        getIpMoveResult(currentLine,resultList);
                    }
                    //4、<省1> 疑似患者 流入 <省2> n人
                    else if(currentLine.matches(regular.regularFour)){
                       getSpMoveResult(currentLine,resultList);
                    }
                    //5、<省> 死亡 n人
                    else if(currentLine.matches(regular.regularFive)){
                       getDeadResult(currentLine,resultList);
                    }
                    //6、<省> 治愈 n人
                    else if(currentLine.matches(regular.regularSix)){
                        getCureResult(currentLine,resultList);
                    }
                    //7、<省> 疑似患者 确诊感染 n人
                    else if(currentLine.matches(regular.regularSeven)){
                        getSpToIpResult(currentLine,resultList);
                    }
                    //8、<省> 排除 疑似患者 n人
                    else if(currentLine.matches(regular.regularEight)){
                       getSpClearResult(currentLine,resultList);
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
    /*public static Result getIpResult(String currentLine){
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
    }*/
    public static List<Result> getIpResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 新增");
        Pattern pattern2 = Pattern.compile("感染患者 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            for (Result provinceResult:provincesResult
                 ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setIp(Integer.parseInt(matcher2.group(1)));
                    provinceResult.setRefer(true);
                }
            }
            provincesResult.get(0).setIp(Integer.parseInt(matcher2.group(1)));
        }
        return provincesResult;
    }
    /**
     *@描述  当前行为 “<省> 新增 疑似患者 n人”对应语句，将其转换为Result类，获得某省疑似患者变化结果
     *@参数  String currentLine 表示当前行
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
   /* public static Result getSpResult(String currentLine){
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
    }*/
    public static List<Result> getSpResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 新增");
        Pattern pattern2 = Pattern.compile("疑似患者 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setSp(Integer.parseInt(matcher2.group(1)));
                    provinceResult.setRefer(true);
                }
            }
            provincesResult.get(0).setSp(Integer.parseInt(matcher2.group(1)));
        }
        return provincesResult;
    }
    /**
     *@描述  当前行为 “<省> 治愈 n人”对应语句，将其转换为Result类，获得某省治愈患者变化结果
     *@参数  String currentLine 当前行
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
   /*public static Result getCureResult(String currentLine){
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
    }*/
    public static List<Result> getCureResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 治愈");
        Pattern pattern2 = Pattern.compile("治愈 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setCure(Integer.parseInt(matcher2.group(1)));
                    provinceResult.setIp(-Integer.parseInt(matcher2.group(1)));
                    provinceResult.setRefer(true);
                }
            }
            provincesResult.get(0).setCure(Integer.parseInt(matcher2.group(1)));
            provincesResult.get(0).setIp(-Integer.parseInt(matcher2.group(1)));
        }
        return provincesResult;
    }
    /**
     *@描述  当前行为 “<省> 死亡 n人”对应语句，将其转换为Result类，获得某省死亡患者变化结果
     *@参数  String currentLine
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    /*public static Result getDeadResult(String currentLine){
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
    }*/
    public static List<Result> getDeadResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 死亡");
        Pattern pattern2 = Pattern.compile("死亡 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setDead(Integer.parseInt(matcher2.group(1)));
                    provinceResult.setIp(-Integer.parseInt(matcher2.group(1)));
                    provinceResult.setRefer(true);
                }
            }
            provincesResult.get(0).setDead(Integer.parseInt(matcher2.group(1)));
            provincesResult.get(0).setIp(-Integer.parseInt(matcher2.group(1)));
        }
        return provincesResult;
    }

    /**
     *@描述 当前行为 “<省1> 感染患者 流入 <省2> n人”对应语句，将其转换为Result类，获得两省感染患者变化结果
     *@参数 String currentLine 当前行
     *@返回值  List<Result>
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    /*public static List<Result> getIpMoveResult(String currentLine){
        List<Result> resultList = new ArrayList<Result>();
        Pattern pattern1 = Pattern.compile("(.*) 感染患者");
        Pattern pattern2 = Pattern.compile("流入 (.*) \\d+人");
        Pattern pattern3 = Pattern.compile("\\W+ (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        Matcher matcher3 = pattern3.matcher(currentLine);
        Result result1 = new Result();
        Result result2 = new Result();
        if(matcher1.find()&&matcher2.find()){
            result1.setProvince(matcher1.group(1));
            result2.setProvince(matcher2.group(1));
        }
        if(matcher3.find()){
            result1.setIp(-Integer.parseInt(matcher3.group(1)));
            result2.setIp(Integer.parseInt(matcher3.group(1)));
        }
        resultList.add(result1);
        resultList.add(result2);
        return resultList;
    }*/
    public static List<Result> getIpMoveResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 感染患者");
        Pattern pattern2 = Pattern.compile("流入 (.*) \\d+人");
        Pattern pattern3 = Pattern.compile("\\W+ (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        Matcher matcher3 = pattern3.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()&&matcher3.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setIp(-Integer.parseInt(matcher3.group(1)));
                    provinceResult.setRefer(true);
                }
                if(provinceResult.province.equals(matcher2.group(1))) {
                    provinceResult.setIp(Integer.parseInt(matcher3.group(1)));
                    provinceResult.setRefer(true);
                }
            }
        }
        return provincesResult;
    }
    /**
     *@描述 当前行为 “<省1> 疑似患者 流入 <省2> n人”对应语句，将其转换为Result类，获得两省疑似患者变化结果
     *@参数  String currentLine
     *@返回值  List<Result>
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
   /* public static List<Result> getSpMoveResult(String currentLine){
        List<Result> resultList = new ArrayList<Result>();
        Pattern pattern1 = Pattern.compile("(.*) 疑似患者");
        Pattern pattern2 = Pattern.compile("流入 (.*) \\d+人");
        Pattern pattern3 = Pattern.compile("\\W+ (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        Matcher matcher3 = pattern3.matcher(currentLine);
        Result result1 = new Result();
        Result result2 = new Result();
        if(matcher1.find()&&matcher2.find()){
            result1.setProvince(matcher1.group(1));
            result2.setProvince(matcher2.group(1));
        }
        if(matcher3.find()){
            result1.setSp(-Integer.parseInt(matcher3.group(1)));
            result2.setSp(Integer.parseInt(matcher3.group(1)));
        }
        resultList.add(result1);
        resultList.add(result2);
        return resultList;
    }*/
    public static List<Result> getSpMoveResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 疑似患者");
        Pattern pattern2 = Pattern.compile("流入 (.*) \\d+人");
        Pattern pattern3 = Pattern.compile("\\W+ (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        Matcher matcher3 = pattern3.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()&&matcher3.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setSp(-Integer.parseInt(matcher3.group(1)));
                    provinceResult.setRefer(true);
                }
                if(provinceResult.province.equals(matcher2.group(1))) {
                    provinceResult.setSp(Integer.parseInt(matcher3.group(1)));
                    provinceResult.setRefer(true);
                }
            }
        }
        return provincesResult;
    }
    /**
     *@描述 当前行为 “<省> 疑似患者 确诊感染 n人”对应语句，将其转换为Result类，获得某省疑似患者、感染患者变化结果
     *@参数  String currentLine
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    /*public static Result getSpToIpResult(String currentLine){
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
     */
    public static List<Result> getSpToIpResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 疑似患者");
        Pattern pattern2 = Pattern.compile("确诊感染 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setSp(-Integer.parseInt(matcher2.group(1)));
                    provinceResult.setIp(Integer.parseInt(matcher2.group(1)));
                    provinceResult.setRefer(true);
                }
            }
            provincesResult.get(0).setSp(-Integer.parseInt(matcher2.group(1)));
            provincesResult.get(0).setIp(Integer.parseInt(matcher2.group(1)));
        }
        return provincesResult;
    }

    /**
     *@描述  当前行为 “<省> 排除 疑似患者 n人”对应语句，将其转换为Result类，获得某省疑似患者变化结果
     *@参数  String currentLine
     *@返回值  Result
     *@创建人  221701101林露
     *@创建时间  2020/2/13
     */
    /*public static Result getSpClearResult(String currentLine){
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
*/
    public static List<Result> getSpClearResult(String currentLine,List<Result> provincesResult){
        Pattern pattern1 = Pattern.compile("(.*) 排除");
        Pattern pattern2 = Pattern.compile("疑似患者 (.*)人");
        Matcher matcher1 = pattern1.matcher(currentLine);
        Matcher matcher2 = pattern2.matcher(currentLine);
        if(matcher1.find()&&matcher2.find()){
            for (Result provinceResult:provincesResult
            ) {
                if(provinceResult.province.equals(matcher1.group(1))) {
                    provinceResult.setSp(-Integer.parseInt(matcher2.group(1)));
                    provinceResult.setRefer(true);
                }
            }
            provincesResult.get(0).setSp(-Integer.parseInt(matcher2.group(1)));
        }
        return provincesResult;
    }
    /**
     *@描述 获得parameters.date之前所有日志文件
     *@参数  Parameter
     *@返回值  List<String>
     *@创建人  221701101林露
     *@创建时间  2020/2/12
     */
    public static List<String> getLogFiles(Parameters parameters){
        File file = new File(parameters.log);
        File[] fileList = file.listFiles();
        String fileName = null;
        String latest = fileList[0].getName().substring(0,10);
        List<String> logFiles = new ArrayList<String>();
        try{
            for (int i = 0; i < fileList.length; i++) {
                fileName = fileList[i].getName();
                if(fileName.substring(0,10).compareTo(latest) > 0)
                    latest = fileName.substring(0,10);
                if(parameters.date != null){
                    if (fileName.substring(0,10).compareTo(parameters.date) <= 0) { //如果该文件的日期小于指定日期
                        logFiles.add(parameters.log + "\\" + fileName);
                        //System.out.println(parameters.log+ "\\" + fileName);
                    }
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        if(parameters.date == null)
            parameters.date = latest;
        return logFiles;
    }

    public static Parameters ParseOptions(String[] args) {
        Parameters parameters = new Parameters();
        parameters.setArgs(args);
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
                if (i == args.length - 1)
                    //parameters.date = "default";
                    continue;
                else {
                    if (args[i + 1].substring(0, 1).equals("-"))
                        //parameters.date="default";
                        continue;
                    else {
                        parameters.date = args[i + 1];
                        i++;
                    }
                }
            } else if (args[i].equals("-type")) {
                for (int j = i+1; j < args.length; j++) {
                    if(!args[j].substring(0, 1).equals("-")) {
                        typeList.add(args[j]);
                        i++;
                    }
                    else
                        break;
                }
            } else if (args[i].equals("-province")) {
                for (int j = i+1; j < args.length; j++) {
                    if(!args[j].substring(0, 1).equals("-")) {
                        provinceList.add(args[j]);
                        i++;
                    }
                    else
                        break;
                }
            }
        }
        parameters.type = typeList;
        parameters.province = provinceList;
        return parameters;
    }
}

