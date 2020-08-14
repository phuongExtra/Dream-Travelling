/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhatp
 */
public class Utilities {

    public static List<String> getImageList() {
        List<String> fileName = new ArrayList<>();
        String defaultPath = Utilities.class.getProtectionDomain().getCodeSource().getLocation().toString();

        String folderPath = defaultPath.substring(5, defaultPath.length() - 26);
        File directory = new File(folderPath + "web/img/");
        if (directory.exists()) {
            File[] fileList = directory.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                String name = fileList[i].getName();
                String extension = name.substring(name.length() - 3);
                if ((extension.equals("jpg") || extension.equals("png"))) {
                    fileName.add(name);
                }
            }
        }
        return fileName;
    }

    public static List<String> getSrcList(String src) {
        List<String> fileName = new ArrayList<>();
        String defaultPath = Utilities.class.getProtectionDomain().getCodeSource().getLocation().toString();

        String folderPath = defaultPath.substring(5, defaultPath.length() - 26);
        File directory = new File(folderPath + "web/" + src + "/");
        if (directory.exists()) {
            File[] fileList = directory.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                String name = fileList[i].getName();
                fileName.add(name);
            }
        }
        return fileName;
    }

}
