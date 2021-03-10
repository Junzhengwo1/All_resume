package ThreadAndRunnable.downLoader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author JIAJUN KOU
 */
public class Loader extends Thread{

    private String url;
    private String name;

    public Loader(String url,String name){
        this.url=url;
        this.name=name;
    }

    @Override
    public void run() {
        WebDownLoader webDownLoader = new WebDownLoader();
        webDownLoader.downLoad(url,name);
        System.out.println("下载文件名"+name);

    }
    public static void main(String[] args) {
        Loader loader = new Loader("https://dgss2.bdstatic.com/5eR1dDebRNRTm2_p8IuM_a/her/static/indexnew/container/search/baidu-logo.ba9d667.png","baidu.png");

        loader.start();

    }
}




class WebDownLoader{

    /**
     * 下载方法
     */
    public void downLoad(String url,String name){
        try {
            FileUtils.copyURLToFile(new URL(url),new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("downLoader方法出现问题");
        }
    }

}