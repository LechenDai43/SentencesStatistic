/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;

/**
 *
 * @author LeLe
 */
public class PhraseBookDriver extends ChromeDriver{
    private ArrayList<String> lessons = new ArrayList<String>();
    private boolean isZH;
    private String name;
    
    public PhraseBookDriver(String address, boolean classifer, String language){
        name = language;
        isZH = classifer;
        this.get(address);
        List<WebElement> columns = this.findElements(By.className("col-md-4"));
        while(columns.size() > 3){
            columns.remove(3);
        }
        List<WebElement> links = columns.get(0).findElements(By.tagName("a"));
        links.addAll(columns.get(1).findElements(By.tagName("a")));
        links.addAll(columns.get(2).findElements(By.tagName("a")));
        for(int i = 0; i < links.size(); i ++){
            lessons.add(links.get(i).getAttribute("href"));
        }
        this.close();
    }
    
    public boolean openLessons(ArrayList<LessonTarget> lesson){
        try{
            for(LessonTarget x : lesson){
                String address = lessons.get(x.lessonId - 1);
                LessonDriver driver = new LessonDriver(address,isZH,name);
                driver.getSentences(x.lessonId,x.target);
                driver.close();
            }
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        
    }
    
    public boolean openLessons(){
        int count = 1;
        try{
            for(String x : lessons){
                LessonDriver driver = new LessonDriver(x,isZH,name);
                if(name.length() == 2){
                    driver.getSentences(count,true);
                }else{
                    driver.getSentences(count,false);
                }
                
                driver.close();
                count ++;
            }
            return true;
        }catch(Exception e){
            System.out.println("Error occurs at " + count + " th lesson.");
            return false;
        }
    }
    
    public String toString(){
        String result = "";
        int count = 1;
        for(String a : lessons){
            result += count + ": " + a + "\n";
            count ++;
        }
        return result;
    }
}
