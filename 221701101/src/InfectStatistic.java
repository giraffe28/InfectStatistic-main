import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public static void main(String[] args) {
        list(args);
    }


    /**
     * @param args
     * @return void
     * function 测试输出
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
     * @param args
     * @return parameters
     */
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
                parameters.date = args[i + 1];
                i++;
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
