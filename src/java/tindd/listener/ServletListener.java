/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Tin
 */
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getRealPath("/");

        FileReader fr = null;
        BufferedReader br = null;
        Map<String, String> addressMap = null;

        try {
            File file = new File(path + "/WEB-INF/address.txt");
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = br.readLine();

            if (line != null) {
                addressMap = new HashMap<>();

                do {
                    String[] temp = line.split("=");

                    //Format: virtual=real
                    String virtualAddr = temp[0];
                    String realAddr = temp[1];

                    addressMap.put(virtualAddr, realAddr);

                    line = br.readLine();
                } while (line != null);
            } //end if line not null

            if (addressMap != null) {
                context.setAttribute("ADDRESS_MAP", addressMap);
            } //end address map not null
        } catch (FileNotFoundException ex) {
            context.log("ServletListener _ FileNotFound: " + ex.getMessage());
        } catch (IOException ex) {
            context.log("ServletListener _ IOException: " + ex.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    context.log("ServletListener _ BufferedReader Closing _ IOException: " + ex.getMessage());
                }
            }

            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    context.log("ServletListener _ FileReader Closing _ IOException: " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("ADDRESS_MAP");
    }
}
