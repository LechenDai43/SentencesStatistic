/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author LeLe
 */
public class SentenceStatistics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        //Chinese Hebrew Telugu
        System.setProperty("webdriver.chrome.driver", 
                "C:\\Users\\LeLe.PJZ-PC\\Desktop\\external java jars\\selenium\\drivers\\chromedriver.exe");
        
        Classifier frame = new Classifier("这是一个Classifier");
        //Filter frame = new Filter("这是一个Filter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

//        ArrayList<String> language = new ArrayList<String>();
//        language.add("Danish");
//        for(String str : language){
//            FiftyLangDriver frame = new FiftyLangDriver(str);
//            frame.openPhraseBook();
//            frame.close();
//        }
        
        
        
    }
    
}
