/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author LeLe
 */
public class LessonDriver extends ChromeDriver{
    private boolean isZH;
    private String name;
    
    public LessonDriver(String address, boolean classifier,String languagename){
        this.get(address);
        isZH = classifier;
        name = languagename;
    }
    
    public boolean getSentences(int count, ArrayList<Integer> target){
        try{
            List<WebElement> colmds = this.findElementsByClassName("col-md-12");
            WebElement the_colmd = colmds.get(2);
            List<WebElement> right = the_colmd.findElements(By.className("Stil45"));
            ArrayList<String> right_sentences = new ArrayList<String>();
            for(int i = 0; i < right.size(); i ++){
                for(Integer x : target){
                    if(x == i){
                        getOneRightSentence(right.get(i),right_sentences, false);
                        break;
                    }
                }                
            }
            MySQLFiftyLangConnecter connecter;
            if(isZH){
                ArrayList<String> left_sentences = new ArrayList<String>();
                List<WebElement> left = the_colmd.findElements(By.className("Stil35"));
                for(int i = 0; i < left.size(); i ++){
                    for(Integer x : target){
                        if(x == i){
                            getOneLeftSentence(left.get(i),left_sentences);
                            break;
                        }
                    }        
                }
                connecter = new ZHConnecter(name,left_sentences,right_sentences);
            }else{
                connecter = new OtherConnecter(name,right_sentences);
            }
            connecter.updateTable(count);
            connecter.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean getSentences(int count, boolean transliteration){
        try{
            List<WebElement> colmds = this.findElementsByClassName("col-md-12");
            WebElement the_colmd = colmds.get(2);
            List<WebElement> right = the_colmd.findElements(By.className("Stil45"));
            ArrayList<String> right_sentences = new ArrayList<String>();
            for(WebElement x : right){
                getOneRightSentence(x,right_sentences,transliteration);
            }
            MySQLFiftyLangConnecter connecter;
            if(isZH){
                ArrayList<String> left_sentences = new ArrayList<String>();
                List<WebElement> left = the_colmd.findElements(By.className("Stil35"));
                for(int i = 0; i < left.size(); i ++){
                    getOneLeftSentence(left.get(i),left_sentences);
                }
                connecter = new ZHConnecter(name,left_sentences,right_sentences);
            }else{
                connecter = new OtherConnecter(name,right_sentences);
            }
            connecter.updateTable(count);
            connecter.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private void getOneRightSentence(WebElement x, ArrayList<String> right_sentences, boolean transliteration) throws InterruptedException {
        List<WebElement> links = x.findElements(By.tagName("a"));
        WebElement comp;
        if(links.size() > 1){
            links.get(0).click();
            Thread.sleep(50);
            comp = links.get(1);
        }else{
            comp = x;
        }
        String sentence = comp.getText();
        List<WebElement> transli = comp.findElements(By.tagName("span"));
        if(transli.size() > 0){
            String remove = transli.get(0).getText();
            if(transliteration){
                sentence = remove;
            }else{
                sentence = sentence.replace(remove, "");
            }            
        }
        sentence = sentence.replaceAll("\n","");
        right_sentences.add(sentence);
        
    }

    private void getOneLeftSentence(WebElement get, ArrayList<String> left_sentences) {
        String sentence = get.getText();
        sentence = sentence.replaceAll("\n", "");
        left_sentences.add(sentence);
    }
    
}
