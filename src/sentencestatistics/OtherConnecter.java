/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeLe
 */
public class OtherConnecter extends MySQLFiftyLangConnecter{
    private ArrayList<String> list;

    public OtherConnecter(String table, ArrayList<String> sentence) throws SQLException {
        super(table);
        list = sentence;
    }

    @Override
    public void updateTable(int count) {
        count = (count * 100) + 1;
        for(int i = 0; i < list.size(); i ++){
            String update = "insert into " + tableName + " value(\"" + count + "\",\"" + list.get(i) + "\")";
            try {
                statement.executeUpdate(update);
            } catch (Exception ex) {
                Logger.getLogger(ZHConnecter.class.getName()).log(Level.SEVERE, null, ex);
            }
            count ++;
        }
    }
    
}
