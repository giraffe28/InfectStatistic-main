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
    public void initLog() {
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
    public void list() {
    }

    @org.junit.Test
    public void getLogFiles() {
    }

    @org.junit.Test
    public void parseOptions() {
        String command1 = "java InfectStatistic list -date 2020-01-22 -log D:/log/ -out D:/output.txt";
        String command2 = "java InfectStatistic list -date 2020-01-22 -log D:/log/ -out D:/output.txt";
        String[] array1 = command1.split("\\s");
        InfectStatistic.Parameters parameter = InfectStatistic.ParseOptions(array1);
        Assert.assertEquals("log: D:/log/ out: D:/output.txt date: 2020-01-22 type: province: ", parameter.getParameterString());
    }
}