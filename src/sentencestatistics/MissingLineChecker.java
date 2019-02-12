/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author LeLe
 */
public class MissingLineChecker {
    
    public MissingLineChecker(){
    }
    
    public ArrayList<LessonTarget> getMissingLine(String name) throws SQLException{
        ArrayList<LessonTarget> result = new ArrayList<LessonTarget>();
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8", 
                "root", "0921");
        Statement statement = connection.createStatement();
        for(int i = 1; i < 101; i ++){
            ArrayList<Integer> globe = generateEighteen();
            String num = i + "";
            String query = "select id from " + name + " where id like \'" + num + "%\'";
            try{
                ResultSet rs = statement.executeQuery(query);
                ArrayList<Integer> temlist = new ArrayList<Integer>();
                while(rs.next()){
                    String id = (String)rs.getObject("id");
                    int lineId = Integer.parseInt(id) % 100;
                    temlist.add(lineId);
                }
                for(Integer x : temlist){
                    globe.remove(x);
                }
                if(globe.size() > 0){                    
                    LessonTarget one = new LessonTarget();
                    one.lessonId = i;
                    one.target = globe;
                    result.add(one);
                }
            }catch(Exception e){
                System.out.println(e);
            }
            
        }
        return result;
    }

    private ArrayList<Integer> generateEighteen() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(1);
        result.add(2);
        result.add(3);
        result.add(4);
        result.add(5);        
        result.add(6);
        
        result.add(7);
        result.add(8);
        result.add(9);
        result.add(10);
        result.add(11);        
        result.add(12);
        
        result.add(13);
        result.add(14);
        result.add(15);
        result.add(16);
        result.add(17);        
        result.add(18);
        
        return result;
    }
}
