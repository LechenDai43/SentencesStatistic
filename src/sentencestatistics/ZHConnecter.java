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
public class ZHConnecter extends MySQLFiftyLangConnecter{
    private ArrayList<String> right, left;

    public ZHConnecter(String table, ArrayList<String> en, ArrayList<String> zh) throws SQLException {
        super(table);
        right = zh;
        left = en;
    }

    @Override
    public void updateTable(int count) {
        count = (count * 100) + 1;
        for(int i = 0; i < right.size() && i < left.size(); i ++){
            String update = "insert into " + tableName + " value(\"" + count + "\",\"" + right.get(i) + "\",\"" + left.get(i) + "\")";
            try {
                statement.execute(update);
            } catch (Exception ex) {
                Logger.getLogger(ZHConnecter.class.getName()).log(Level.SEVERE, null, ex);
            }
            count ++;
        }
    }
    
}
