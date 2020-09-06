import com.bj58.dia.rec.gul.wpai.dlpredictonline.contract.IWpaiDLPredictOnlineService;
import com.bj58.spat.scf.client.SCFInit;
import com.bj58.spat.scf.client.proxy.builder.ProxyFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class checkImage {
    private static IWpaiDLPredictOnlineService wpaiDLPredictOnlineService = null;

    static {
        // 服务初始化
        SCFInit.initScfKey("/Users/gzh/IdeaProjects/kite/58_image_check/src/main/resources/scf.key");
        String url = "tcp://wpaidlpredictonline/WpaiDLPredictOnlineService";
        wpaiDLPredictOnlineService = ProxyFactory.create(IWpaiDLPredictOnlineService.class, url);
    }

    public static void main(String[] args) throws Exception {

        // 配置文件配置测试的图片目录和wpai task id
        Properties prop = new Properties();

        InputStream configStream = checkImage.class.getResourceAsStream("config.properties");
        prop.load(configStream);

        String picPath = prop.getProperty("picpath");
        int taskId = Integer.parseInt(prop.getProperty("taskid"));


        // 测试图片放入数据list
        File file = new File(picPath);
        File[] files = file.listFiles();
        List<Object> dataList = new ArrayList<Object>();
        for (File f : files) {
            if (f.getName().endsWith(".jpg")) {
                byte[] src = readFile(f.getAbsolutePath());
                dataList.add(src);
            }
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~Call Start~~~~~~~~~~~~~~~~~~~~~~~~~~");
        long begin = System.currentTimeMillis();

        //get result
        Object result = wpaiDLPredictOnlineService.tensorflowServingPredictOnline(taskId, dataList);
        System.out.println(result);

        System.out.println("~~~~~~~~~~~~~~~~~~~Call End~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        long end = System.currentTimeMillis();
        System.out.println("call for " + (end - begin) + "ms");

    }

    /**
     * 读取文件
     */
    private static byte[] readFile(String file) throws Exception {
        byte[] data;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
        } finally {
            in.close();
        }
        return data;
    }


}
