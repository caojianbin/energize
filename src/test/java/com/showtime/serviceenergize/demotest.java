package com.showtime.serviceenergize;

import com.showtime.serviceenergize.utils.DateUtil;
import com.showtime.serviceenergize.utils.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.*;
import java.util.Date;

/**
 * <p>
 * demotest
 * </p>
 *
 * @author cjb
 * @since 9:19
 */
@SpringBootTest
@SpringJUnitWebConfig
public class demotest {

    @Test
    public void FileTest()throws Exception {
        String str = "0";
        int parseInt = Integer.parseInt(str);
        System.out.println(parseInt);
    }

    @Test
    public void FileInPut(){
        String str = "2";
    }

    public static String htmlDecode(String string) {
        if (null == string || "".equals(string))
            return null;
        else {
            String result = string;
            result = result.replaceAll("&", "&");
            result = result.replaceAll("<", "<");
            result = result.replaceAll(">", ">");
            result = result.replaceAll("", "\"");
            return (result.toString());
        }
    }

    public static String htmlEncode(String string) {
        if(null == string || "".equals(string))
            return null;
        else{
            String result = string;
            result = result.replaceAll("&", "&");
            result = result.replaceAll("<", "<");
            result = result.replaceAll(">", ">");
            result = result.replaceAll("\"","");
            return (result.toString());
        }
    }



    @Test
    public void run(){
        String s = "F:\\test.txt";
    }
}

