import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 221701101linlu
 * @date: $ $
 * @version: v 1.0
 * @decsription: $
 */
public class InfectStatisticTest {

    @org.junit.Test
    public void main() {
        //命令不是以list开头，程序报告错误
        String[] args1 = { "-log", "D:\\log\\", "-out", "D:\\ListOut1.txt"};
        //未输入-log参数的值，或直接不输入
        String[] args2 = { "list","-log","-out", "D:\\ListOut2.txt"};
        String[] args3 = { "list","-out", "D:\\ListOut3.txt"};
        //未输入-out参数的值，或直接不输入
        String[] args4 = { "list","-log", "D:\\log\\", "-out"};
        String[] args5 = { "list","-log", "D:\\log\\"};
        //仅携带-log和-out参数
        String[] args6 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut4.txt"};
        //携带-log、-out、-date
        String[] args7 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut5.txt", "-date", "2020-01-22"};
        String[] args8 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut5.txt", "-date", "2020-01-23"};
        String[] args9 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut5.txt", "-date", "2020-01-27"};
        //携带-log、-out、-date、-type
        String[] args10 = {"list","-log", "D:\\log\\", "-out", "D:\\ListOut6.txt","-date", "2020-01-22","-type","dead"};
        String[] args11= {"list","-log", "D:\\log\\", "-out", "D:\\ListOut6.txt","-date", "2020-01-23","-type","cure"};
        //携带-log、-out、-date、province
        String[] args12 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut7.txt", "-date", "2020-01-22", "-province", "福建", "河北"};
        //携带-log、-out、-date、-type、-province
        String[] args13 = { "list", "-log", "D:\\log\\", "-out", "D:\\ListOut8.txt", "-date", "2020-01-23", "-type", "cure", "dead", "ip", "-province", "全国", "浙江", "福建"};
        //携带-log、-out、-type
        String[] args14 = {"list","-log", "D:\\log\\", "-out", "D:\\ListOut.txt","-type","ip"};
        //携带-log、-out、-type、-province
        String[] args15 = {"list","-log", "D:\\log\\", "-out", "D:\\ListOut.txt","-type","ip","sp","-province","福建"};
        //携带-log、-out、-province
        String[] args16 = {"list","-log", "D:\\log\\", "-out", "D:\\ListOut.txt","-province","河北"};
        InfectStatistic.main(args1);
       InfectStatistic.main(args2);
        InfectStatistic.main(args3);
        InfectStatistic.main(args4);
        InfectStatistic.main(args5);
        InfectStatistic.main(args6);
        InfectStatistic.main(args7);
        InfectStatistic.main(args8);
        InfectStatistic.main(args9);
        InfectStatistic.main(args10);
        InfectStatistic.main(args11);
        InfectStatistic.main(args12);
        InfectStatistic.main(args13);
        InfectStatistic.main(args14);
        InfectStatistic.main(args15);
        InfectStatistic.main(args16);
    }


    @Test
    public void print() {
        InfectStatistic.print("D:\\ListOut1.txt");
    }

    @org.junit.Test
    public void parseLog() {
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        InfectStatistic.parseLog("D:\\log\\2020-01-22.log.txt", list.resultList);
        String resultString = "";
        for (InfectStatistic.Result result : list.resultList
        ) {
            if (result.isRefer == true)
                resultString += result.getResultString();
        }
        String correctString =
                "福建 感染患者5人 疑似患者7人 治愈0人 死亡0人" +
                        "湖北 感染患者10人 疑似患者15人 治愈2人 死亡1人";
        Assert.assertEquals(correctString, resultString);

    }

    @org.junit.Test
    public void getIpResult() {
        String testResult = null;
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getIpResult("福建 新增 感染患者 23人", provinceList);
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("福建");
        correctResult.setIp(23);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "福建")
                testResult = current.getResultString();
        }
        Assert.assertEquals(correctResult.getResultString(), testResult);
    }

    @org.junit.Test
    public void getSpResult() {
        String testResult = null;
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getSpResult("福建 新增 疑似患者 23人", provinceList);
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("福建");
        correctResult.setSp(23);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "福建")
                testResult = current.getResultString();
        }
        Assert.assertEquals(correctResult.getResultString(), testResult);
    }

    @org.junit.Test
    public void getCureResult() {
        String testResult = null;
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getCureResult("新疆 治愈 3人", provinceList);
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("新疆");
        correctResult.setIp(-3);
        correctResult.setCure(3);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "新疆")
                testResult = current.getResultString();
        }
        Assert.assertEquals(correctResult.getResultString(), testResult);
    }

    @org.junit.Test
    public void getDeadResult() {
        String testResult = null;
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getDeadResult("安徽 死亡 2人", provinceList);
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("安徽");
        correctResult.setIp(-2);
        correctResult.setDead(2);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "安徽")
                testResult = current.getResultString();
        }
        Assert.assertEquals(correctResult.getResultString(), testResult);
    }

    @org.junit.Test
    public void getIpMoveResult() {
        String testResult = "";
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getIpMoveResult("浙江 感染患者 流入 福建 12人", provinceList);
        InfectStatistic.Result correctResult1 = new InfectStatistic.Result();
        InfectStatistic.Result correctResult2 = new InfectStatistic.Result();
        correctResult1.setProvince("福建");
        correctResult1.setIp(12);
        correctResult2.setProvince("浙江");
        correctResult2.setIp(-12);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "浙江")
                testResult += current.getResultString();
            if (current.province == "福建")
                testResult += current.getResultString();
        }
        Assert.assertEquals(correctResult1.getResultString() + correctResult2.getResultString(), testResult);
    }

    @org.junit.Test
    public void getSpMoveResult() {
        String testResult = "";
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getSpMoveResult("湖北 疑似患者 流入 福建 2人", provinceList);
        InfectStatistic.Result correctResult1 = new InfectStatistic.Result();
        InfectStatistic.Result correctResult2 = new InfectStatistic.Result();
        correctResult1.setProvince("福建");
        correctResult1.setSp(2);
        correctResult2.setProvince("湖北");
        correctResult2.setSp(-2);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "福建")
                testResult += current.getResultString();
            if (current.province == "湖北")
                testResult += current.getResultString();
        }
        Assert.assertEquals(correctResult1.getResultString() + correctResult2.getResultString(), testResult);
    }

    @org.junit.Test
    public void getSpToIpResult() {
        String testResult = null;
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getSpToIpResult("福建 疑似患者 确诊感染 2人", provinceList);
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("福建");
        correctResult.setSp(-2);
        correctResult.setIp(2);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "福建")
                testResult = current.getResultString();
        }
        Assert.assertEquals(correctResult.getResultString(), testResult);
    }

    @org.junit.Test
    public void getSpClearResult() {
        String testResult = null;
        InfectStatistic.ResultList list = new InfectStatistic.ResultList();
        List<InfectStatistic.Result> provinceList = list.resultList;
        InfectStatistic.getSpClearResult("新疆 排除 疑似患者 5人", provinceList);
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("新疆");
        correctResult.setSp(-5);
        for (InfectStatistic.Result current : provinceList
        ) {
            if (current.province == "新疆")
                testResult = current.getResultString();
        }
        Assert.assertEquals(correctResult.getResultString(), testResult);
    }

    @org.junit.Test
    public void getLogFiles() {
        ArrayList<String> type = new ArrayList<String>();
        ArrayList<String> province = new ArrayList<String>();
        String testResult = "";
        InfectStatistic.Parameters parameters =new InfectStatistic.Parameters("D:\\log","D:\\软件工程作业\\InfectStatistic-main\\221701101","2020-01-27",type,province);
        List<String> logFiles = InfectStatistic.getLogFiles(parameters);
        for (String str:logFiles
             ) {
            testResult += str + " ";
        }
        Assert.assertEquals("D:\\log\\2020-01-22.log.txt D:\\log\\2020-01-23.log.txt D:\\log\\2020-01-27.log.txt ", testResult);
    }

    @org.junit.Test
    public void parseOptions() {
        String command1 = "list -date 2020-01-22 -log D:/log/ -out D:/output.txt";
        String command2 = "list -date -log D:/log/ -out D:/output.txt";
        String command3 = "list -log D:/log/ -out D:/output.txt";
        String command4 = "list -log D:/log/ -out D:/output.txt -type ip sp -province 全国 浙江";
        String command5 = "list -log D:/log/ -out D:/output.txt -province 全国 浙江 -type ip";
        String[] array1 = command1.split("\\s+");
        String[] array2 = command2.split("\\s+");
        String[] array3 = command3.split("\\s+");
        String[] array4 = command4.split("\\s+");
        String[] array5 = command5.split("\\s+");
        InfectStatistic.Parameters parameter1 = InfectStatistic.parseOptions(array1);
        InfectStatistic.Parameters parameter2 = InfectStatistic.parseOptions(array2);
        InfectStatistic.Parameters parameter3 = InfectStatistic.parseOptions(array3);
        InfectStatistic.Parameters parameter4 = InfectStatistic.parseOptions(array4);
        InfectStatistic.Parameters parameter5 = InfectStatistic.parseOptions(array5);
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: 2020-01-22 type: province: ", parameter1.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: province: ", parameter2.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: province: ", parameter3.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: ip sp province: 全国 浙江 ", parameter4.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: ip province: 全国 浙江 ", parameter5.getParameterString());
    }
}