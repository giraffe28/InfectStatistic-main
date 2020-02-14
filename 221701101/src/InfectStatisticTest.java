import org.junit.Assert;

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
        String[] args = { "java", "InfectStatistic" ,"list","-date", "2020-01-22", "-log", "D:\\2020-01-22.log.txt", "-out", "D:/output.txt"};
        InfectStatistic.main(args);
    }

    @org.junit.Test
    public void parseLog() {
        List<InfectStatistic.Result> list = InfectStatistic.parseLog("D:\\2020-01-22.log.txt");
        String resultString = "";
        for (InfectStatistic.Result result:list
             ) {
            resultString += result.getResultString();
        }
        String correctString = "福建 感染患者2人 疑似患者0人 治愈0人 死亡0人"
                +"福建 感染患者0人 疑似患者5人 治愈0人 死亡0人"
                +"湖北 感染患者15人 疑似患者0人 治愈0人 死亡0人"
                +"湖北 感染患者0人 疑似患者20人 治愈0人 死亡0人"
                +"湖北 感染患者-2人 疑似患者0人 治愈0人 死亡0人"
                +"福建 感染患者2人 疑似患者0人 治愈0人 死亡0人"
                +"湖北 感染患者0人 疑似患者-3人 治愈0人 死亡0人"
                +"福建 感染患者0人 疑似患者3人 治愈0人 死亡0人"
                +"湖北 感染患者-1人 疑似患者0人 治愈0人 死亡1人"
                +"湖北 感染患者-2人 疑似患者0人 治愈2人 死亡0人"
                +"福建 感染患者1人 疑似患者-1人 治愈0人 死亡0人"
                +"湖北 感染患者0人 疑似患者-2人 治愈0人 死亡0人";
        Assert.assertEquals(correctString, resultString);

    }

    @org.junit.Test
    public void getIpResult() {
        InfectStatistic.Result testResult = InfectStatistic.getIpResult("福建 新增 感染患者 23人");
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("福建");
        correctResult.setIp(23);
        Assert.assertEquals(correctResult.getResultString(), testResult.getResultString());
    }

    @org.junit.Test
    public void getSpResult() {
        InfectStatistic.Result testResult = InfectStatistic.getSpResult("福建 新增 疑似患者 23人");
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("福建");
        correctResult.setSp(23);
        Assert.assertEquals(correctResult.getResultString(), testResult.getResultString());
    }

    @org.junit.Test
    public void getCureResult() {
        InfectStatistic.Result testResult = InfectStatistic.getCureResult("新疆 治愈 3人");
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("新疆");
        correctResult.setIp(-3);
        correctResult.setCure(3);
        Assert.assertEquals(correctResult.getResultString(), testResult.getResultString());
    }

    @org.junit.Test
    public void getDeadResult() {
        InfectStatistic.Result testResult = InfectStatistic.getDeadResult("安徽 死亡 2人");
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("安徽");
        correctResult.setDead(2);
        correctResult.setIp(-2);
        Assert.assertEquals(correctResult.getResultString(), testResult.getResultString());
    }

    @org.junit.Test
    public void getIpMoveResult() {
        List<InfectStatistic.Result> testResult = InfectStatistic.getIpMoveResult("浙江 感染患者 流入 福建 12人");
        List<InfectStatistic.Result> correctResult = new ArrayList<InfectStatistic.Result>();
        InfectStatistic.Result result1 = new InfectStatistic.Result();
        InfectStatistic.Result result2 = new InfectStatistic.Result();
        result1.setProvince("浙江");
        result1.setIp(-12);
        result2.setProvince("福建");
        result2.setIp(12);
        correctResult.add(result1);
        correctResult.add(result2);
        Assert.assertEquals(correctResult.get(0).getResultString(), testResult.get(0).getResultString());
        Assert.assertEquals(correctResult.get(1).getResultString(), testResult.get(1).getResultString());
    }

    @org.junit.Test
    public void getSpMoveResult() {
        List<InfectStatistic.Result> testResult = InfectStatistic.getSpMoveResult("湖北 疑似患者 流入 福建 2人");
        List<InfectStatistic.Result> correctResult = new ArrayList<InfectStatistic.Result>();
        InfectStatistic.Result result1 = new InfectStatistic.Result();
        InfectStatistic.Result result2 = new InfectStatistic.Result();
        result1.setProvince("湖北");
        result1.setSp(-2);
        result2.setProvince("福建");
        result2.setSp(2);
        correctResult.add(result1);
        correctResult.add(result2);
        Assert.assertEquals(correctResult.get(0).getResultString(), testResult.get(0).getResultString());
        Assert.assertEquals(correctResult.get(1).getResultString(), testResult.get(1).getResultString());
    }

    @org.junit.Test
    public void getSpToIpResult() {
        InfectStatistic.Result testResult = InfectStatistic.getSpToIpResult("福建 疑似患者 确诊感染 2人");
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("福建");
        correctResult.setSp(-2);
        correctResult.setIp(2);
        Assert.assertEquals(correctResult.getResultString(), testResult.getResultString());
    }

    @org.junit.Test
    public void getSpClearResult() {
        InfectStatistic.Result testResult = InfectStatistic.getSpClearResult("新疆 排除 疑似患者 5人");
        InfectStatistic.Result correctResult = new InfectStatistic.Result();
        correctResult.setProvince("新疆");
        correctResult.setSp(-5);
        Assert.assertEquals(correctResult.getResultString(), testResult.getResultString());
    }


    @org.junit.Test
    public void getLogFiles() {
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
        InfectStatistic.Parameters parameter1 = InfectStatistic.ParseOptions(array1);
        InfectStatistic.Parameters parameter2 = InfectStatistic.ParseOptions(array2);
        InfectStatistic.Parameters parameter3 = InfectStatistic.ParseOptions(array3);
        InfectStatistic.Parameters parameter4 = InfectStatistic.ParseOptions(array4);
        InfectStatistic.Parameters parameter5 = InfectStatistic.ParseOptions(array5);
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: 2020-01-22 type: province: ", parameter1.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: province: ", parameter2.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: province: ", parameter3.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: ip sp province: 全国 浙江 ", parameter4.getParameterString());
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: type: ip province: 全国 浙江 ", parameter5.getParameterString());
    }
}