/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.util.ArrayList;

/**
 *
 * @author LeLe
 */
public class LessonTarget {
    protected int lessonId = 0;
    protected ArrayList<Integer> target = new ArrayList<Integer>();
    
    public String toString(){
        String result = lessonId + ": ";
        for(Integer x : target){
            result += x + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }
}
