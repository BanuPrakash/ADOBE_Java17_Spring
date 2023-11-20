package client;

import com.adobe.prj.service.AppService;
import com.adobe.prj.util.DateUtil;

public class Main {
    public static void main(String[] args) {
        AppService service = new AppService();
        service.execute();

        DateUtil.getDate();
    }
}
