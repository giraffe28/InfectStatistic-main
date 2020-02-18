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
        String[] args1 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut1.txt", "-date", "2020-01-22"};
        String[] args2 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut2.txt", "-date", "2020-01-22", "-province", "福建", "河北"};
        String[] args3 = { "list", "-log", "D:\\log\\", "-out", "D:\\ListOut7.txt", "-date", "2020-01-23", "-type", "cure", "dead", "ip", "-province", "全国", "浙江", "福建"};
        String[] args4 = {"list", "-log", "D:\\log\\", "-out", "D:\\ListOut1.txt"};
        InfectStatistic.main(args1);
        //InfectStatistic.main(args2);
       // InfectStatistic.main(args3);
       // InfectStatistic.main(args4);
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
        String command1 = "java InfectStatistic list -date 2020-01-22 -log D:/log/ -out D:/output.txt";
        String command2 = "java InfectStatistic list -date -log D:/log/ -out D:/output.txt";
        String command3 = "java InfectStatistic list -log D:/log/ -out D:/output.txt";
        String command4 = "java InfectStatistic list -log D:/log/ -out D:/output.txt -type ip sp -province 全国 浙江";
        String command5 = "java InfectStatistic list -log D:/log/ -out D:/output.txt -province 全国 浙江 -type ip";
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