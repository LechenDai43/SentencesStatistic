/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author LeLe
 */
public class Searcher extends JFrame{
    private JButton query,front, back, jump;
    private JComboBox<String> pages;
    private JLabel english,chinese, word;
    private JPanel jPanel1;
    private JTextField en_key, zh_key,key;
    private String language;
    private int pointer = 0;
    private ArrayList<FourThing> list = new ArrayList<FourThing>();
    
    public Searcher(String name, String a) throws SQLException{
        super(a);
        language = name;
        initComponents();
    }
    
    private void showLabels(){
        jPanel1.removeAll();
        jPanel1.revalidate();
        jPanel1.repaint();
        for(int i = 0; i < 10 && pointer + i < list.size(); i ++){
            JLabel label = new JLabel(list.get(i + pointer).toString());
            jPanel1.add(label);
        }
        jPanel1.revalidate();
        jPanel1.repaint();
        pack();
    }
    
    private void initComponents() throws SQLException {

        english = new javax.swing.JLabel();
        en_key = new javax.swing.JTextField();
        chinese = new javax.swing.JLabel();
        zh_key = new javax.swing.JTextField();
        word = new javax.swing.JLabel();
        key = new javax.swing.JTextField();
        query = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        front = new javax.swing.JButton();
        back = new javax.swing.JButton();
        pages = new javax.swing.JComboBox<>();
        jump = new javax.swing.JButton();

        english.setText("英语");

        chinese.setText("中文");

        word.setText("单词");

        query.setText("查找");
        query.addMouseListener(new QueryListener());

        front.setText("<");
        front.addMouseListener(new BackwardListener());

        back.setText(">");
        back.addMouseListener(new ForwardListener());

        pages.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"1"}));

        jump.setText("跳转");
        jump.addMouseListener(new JumpLinstener());
        
        jPanel1.setPreferredSize(new Dimension(600,400));
        jPanel1.setLayout(new GridLayout(10,1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(english, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(en_key, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chinese, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zh_key, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(word, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(key, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(query, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(front)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jump)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(back)))
                        .addGap(0, 70, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chinese, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(zh_key, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(english, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(en_key, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(word, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(key, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(query)
                    .addComponent(front)
                    .addComponent(back)
                    .addComponent(pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jump))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private class BackwardListener implements MouseListener {

        public BackwardListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int index = pages.getSelectedIndex();
            if(index > 1){
                pointer = pointer - 10;
                pages.setSelectedIndex(index - 1);
                showLabels();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class ForwardListener implements MouseListener {

        public ForwardListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try{
                int index = pages.getSelectedIndex();
                int length = list.size();
                if(pointer + 10 < length){
                    pointer = pointer + 1;
                    pages.setSelectedIndex(1 + index);
                    showLabels();
                }
            }catch(Exception ex){
                
            }
            
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class JumpLinstener implements MouseListener {

        public JumpLinstener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int index = pages.getSelectedIndex();
            String numInStr = pages.getItemAt(index);
            int pageNum = Integer.parseInt(numInStr);
            pointer = pageNum * 10 - 10;
            showLabels();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class FourThing {
        protected String id, en, zh, st;

        public FourThing(String a, String b, String c, String d) {
            id = a;
            zh = b;
            en = c;
            st = d;
        }
        
        public String toString(){
            String result = id;
            int num = 7 - id.length();
            for(int i = 0; i < num; i ++){
                result += " ";
            }
            result += ":   " + st + "        ";
            result += en + "        " + zh;
            return result;
        }
    }

    private class QueryListener implements MouseListener {

        public QueryListener() throws SQLException {        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                String en = en_key.getText(), zh = zh_key.getText(), wd = key.getText();
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8",
                        "root", "0921");
                Statement statement = connection.createStatement();
                ArrayList<String> idList = new ArrayList<String>();
                boolean haveKey = false;
                if(en.length() > 0){
                    haveKey = true;
                    String execution = "select id from chinese where english like \"%" + en + "%\"";
                    ResultSet rs = statement.executeQuery(execution);
                    while(rs.next()){
                        idList.add((String)rs.getObject("id"));
                    }
                    rs.close();
                }   if(zh.length() > 0){
                    String execution = "select id from chinese where chinese like \"%" + zh + "%\"";
                    ResultSet rs = statement.executeQuery(execution);
                    ArrayList<String> tem = new ArrayList<String>();
                    while(rs.next()){
                        tem.add((String)rs.getObject("id"));
                    }
                    rs.close();
                    if(haveKey){
                        for(int i = 0; i < idList.size(); i ++){
                            String str = idList.get(i);
                            if(! tem.contains(str)){
                                idList.remove(i);
                                i --;
                            }
                        }
                    }else{
                        haveKey = true;
                        idList = tem;
                    }
                }   if(wd.length() > 0){
                    String execution = "select id from " + language + " where sentence like \"%" + wd + "%\"";
                    ResultSet rs = statement.executeQuery(execution);
                    ArrayList<String> tem = new ArrayList<String>();
                    while(rs.next()){
                        tem.add((String)rs.getObject("id"));
                    }
                    rs.close();
                    if(haveKey){
                        for(int i = 0; i < idList.size(); i ++){
                            String str = idList.get(i);
                            if(! tem.contains(str)){
                                idList.remove(i);
                                i --;
                            }
                        }
                    }else{
                        idList = tem;
                    }
                }   statement.close();
                connection.close();
                displaySentence(idList);          
            } catch (SQLException ex) {
                Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        private void displaySentence(ArrayList<String> id) throws SQLException {
            list.clear();
            Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8",
                        "root", "0921");
            Statement statement = connection.createStatement();
            ResultSet rs;
            for(String str : id){
                String execution = "select * from chinese where id = \"" + str + "\"";
                rs = statement.executeQuery(execution);
                String ch = "", us = "";
                while(rs.next()){
                    ch = (String)rs.getObject("chinese");
                    us = (String)rs.getObject("english");
                }
                execution = "select * from " + language + " where id = \"" + str + "\"";
                rs = statement.executeQuery(execution);
                String sentence = "";
                while(rs.next()){
                    sentence = (String)rs.getObject("sentence");
                }
                rs.close();
                FourThing ft = new FourThing(str,ch,us,sentence);
                list.add(ft);                
            }
            pointer = 0;
            int numOfPage = list.size() / 10 + (list.size() % 10 == 0 ? 0 : 1);
            String[] cbl = new String[numOfPage];
            for(int i = 0; i < numOfPage; i ++){
                int j = i + 1;
                cbl[i] = j + "";
            }
            pages.setModel(new javax.swing.DefaultComboBoxModel<>(cbl));
            showLabels();
        }
    }
}
