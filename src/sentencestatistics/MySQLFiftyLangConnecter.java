/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author LeLe
 */
public abstract class MySQLFiftyLangConnecter {
    protected Connection connection;
    protected Statement statement;
    protected String tableName;
    
    public MySQLFiftyLangConnecter(String table) throws SQLException{
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8", 
                "root", "0921");
        statement = connection.createStatement();
        tableName = table;
    }
    
    public abstract void updateTable(int count);
    
    public void close() throws SQLException{
        connection.close();
    }
    
}
