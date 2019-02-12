/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.util.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;

/**
 *
 * @author LeLe
 */
public class FiftyLangDriver extends ChromeDriver{
    private WebElement ul;
    private PhraseBookDriver pb_driver;
    private boolean isZH;
    private String name;
    
    public FiftyLangDriver(String LanguageName){
        name = LanguageName;
        this.get("https://www.goethe-verlag.com/book2/EM/");
        List<WebElement> names = this.findElements(By.className("accordion-group"));
        boolean find = false;
        for(int i = 0; i < names.size(); i ++){
            WebElement heading = names.get(i).findElement(By.className("accordion-heading"));
            WebElement link = heading.findElement(By.tagName("a"));
            WebElement head_five = link.findElement(By.tagName("h5"));
            String this_name = head_five.getText();
            if(this_name.contains(LanguageName)){
                if(this_name.contains("Chinese")){
                    isZH = true;
                }else{
                    isZH = false;
                }
                WebElement section = names.get(i);
                ul = section.findElement(By.tagName("ul"));
                names.get(i).click();
                find = true;
                break;
            }
        }   
        if(! find){
            System.out.println(LanguageName + " is not found!!!\n\n\n\n\n\n\n\n" + LanguageName + " is not found!!!");
        }
    }
    
    public boolean openPhraseBook(ArrayList<LessonTarget> lesson){
        try{
            String pb_address = getPhraseBookAddress();
            pb_driver = new PhraseBookDriver(pb_address, isZH,name);
            pb_driver.openLessons(lesson);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean openPhraseBook(){
        try{
            String pb_address = getPhraseBookAddress();
            pb_driver = new PhraseBookDriver(pb_address, isZH,name);
            pb_driver.openLessons();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private String getPhraseBookAddress(){
        List<WebElement> lis = ul.findElements(By.tagName("li"));
        WebElement phrase_li = lis.get(0);
        WebElement section = phrase_li.findElement(By.tagName("section"));
        WebElement h4 = section.findElement(By.tagName("h4"));
        WebElement link = h4.findElement(By.tagName("a"));
        return link.getAttribute("href");
    }
    
}
