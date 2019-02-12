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
import java.io.UnsupportedEncodingException;
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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LeLe
 */
public class Filter extends JFrame{
    private JButton select,front, back, search;
    private JComboBox<String> language, tags;
    private JPanel jPanel1;
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private int pointer = 0;
    
    public Filter(String a) throws SQLException{
        super(a);
        initComponents();
    }
    
    private void initComponents() throws SQLException {

        language = new javax.swing.JComboBox<>();
        tags = new javax.swing.JComboBox<>();
        select = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        front = new javax.swing.JButton();
        back = new javax.swing.JButton();
        search = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        setUpLanguage();
        setUpTags();
        
        select.setText("选择");
        select.addMouseListener(new SelectListener());

        front.setText("向前");
        front.setVisible(false);
        front.addMouseListener(new FrontListener());

        back.setText("向后");
        back.setVisible(false);
        back.addMouseListener(new BackListener());
        
        jPanel1.setPreferredSize(new Dimension(600,400));
        jPanel1.setLayout(new GridLayout(10,1));
        
        search.setText("查询");
        search.setVisible(false);
        search.addMouseListener(new SearchLinstener());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(language, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(tags, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(select, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(front)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(back))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(search)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(language, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tags, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(front)
                    .addComponent(back))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void setUpLanguage() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8", 
                "root", "0921");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from linguistic");
        ArrayList<String> inArrayList = new ArrayList<String>();
        while(rs.next()){
            String oneLanguage = (String)rs.getObject("lang");
            inArrayList.add(oneLanguage);
        }
        int length = inArrayList.size();
        String[] array = new String[length];
        for(int i = 0; i< length; i ++){
            array[i] = inArrayList.get(i);
        }
        language.setModel(new javax.swing.DefaultComboBoxModel<>(array));
        rs.close();
        statement.close();
        connection.close();
    }

    private void setUpTags() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8", 
                "root", "0921");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from tags");
        ArrayList<String> inArrayList = new ArrayList<String>();
        while(rs.next()){
            String oneLanguage = (String)rs.getObject("tag");
            inArrayList.add(oneLanguage);
        }
        int length = inArrayList.size();
        String[] array = new String[length];
        for(int i = 0; i< length; i ++){
            array[i] = inArrayList.get(i);
        }
        tags.setModel(new javax.swing.DefaultComboBoxModel<>(array));
        rs.close();
        statement.close();
        connection.close();
    }
    
    private void showSentences(){
        int size = labels.size();
        ArrayList<JLabel> shownLabel;
        if(size <= 10){
            if(back.isVisible()){
                back.setVisible(false);
                front.setVisible(false);
                this.pack();
            }            
            shownLabel = labels;
        }else{
            if(!back.isVisible()){
               back.setVisible(true);
               front.setVisible(true);
               this.pack();
            }
            shownLabel = new ArrayList<JLabel>();
            for(int i = 0; i < 10 && i + pointer <labels.size(); i ++){
                shownLabel.add(labels.get(i + pointer));
            }
        }
        jPanel1.removeAll();
        jPanel1.revalidate();
        jPanel1.repaint();
        for(JLabel aLabel : shownLabel){
            jPanel1.add(aLabel);
        }
        jPanel1.revalidate();
        jPanel1.repaint();
        checkHidden();
    }

    private void checkHidden() {
        boolean doPack = false;
        if(pointer < 10){
            if(front.isVisible()){
                front.setVisible(false);
                doPack = true;
            }
        }
        if(pointer + 10 >= labels.size()){
            if(back.isVisible()){
                back.setVisible(false);
                doPack = true;
            }
        }
        if(doPack){
            this.pack();
        }
    }

    private class SearchLinstener implements MouseListener {

        public SearchLinstener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                String name;
                int index = language.getSelectedIndex();
                name = language.getItemAt(index);
                Searcher frame = new Searcher(name, "Searching Window");
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            } catch (SQLException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
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

    private class BackListener implements MouseListener {

        public BackListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(back.isVisible()){
                if(pointer >= 10){
                    pointer -= 10;
                    showSentences();
                }
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

    private class FrontListener implements MouseListener {

        public FrontListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(front.isVisible()){
                if(pointer + 10 < labels.size()){
                    pointer += 10;
                    showSentences();
                }
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

    protected class SentenceTagPair {
        private String id,sentence;

        public SentenceTagPair(String a, String b) {
            id = a;
            sentence = b;
        }
        
        public String toString(){
            String result = "";
            int num = id.length();
            num = 6 - num;
            result += id;
            for(int i = 0; i < num; i ++){
                result += " ";
            }
            result += ": " + sentence;
            return result;
        }
    }

    private class SelectListener implements MouseListener {

        public SelectListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            labels.clear();
            try {
                String languageName, tagName;
                int languageNum = language.getSelectedIndex(), tagNum = tags.getSelectedIndex();
                languageName = language.getItemAt(languageNum);
                tagName = tags.getItemAt(tagNum);
                ArrayList<String> sentenceId = new ArrayList<String>();
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8",
                        "root", "0921");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select sentence_id from sentence where tag = \"" + tagName + "\"");
                while(rs.next()){
                    sentenceId.add((String)rs.getObject("sentence_id"));
                }
                ArrayList<SentenceTagPair> collections = new ArrayList<>();
                for(String aId : sentenceId){
                    String command = "select sentence from " + languageName + " where id = \"" + aId + "\"";
                    rs = statement.executeQuery(command);
                    String aSentence = "";
                    while(rs.next()){
                        aSentence = (String)rs.getObject("sentence");
                    }
                    if(aSentence.length() > 0){
                        SentenceTagPair temPair = new SentenceTagPair(aId, aSentence);
                        collections.add(temPair);
                    }
                }
                rs.close();
                statement.close();
                connection.close();
                getSentence(collections);
                search.setVisible(true);
                pack();
            } catch (SQLException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
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

        private void getSentence(ArrayList<SentenceTagPair> collections) throws UnsupportedEncodingException {
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8",
                        "root", "0921");
                Statement statement = connection.createStatement();
                ResultSet rs;
                for(SentenceTagPair pair : collections){
                    String id = pair.id;
                    String command = "select * from chinese where id = \"" + id + "\"";
                    rs = statement.executeQuery(command);
                    String english = "", chinese = "";
                    while(rs.next()){
                        english = (String) rs.getObject("english");
                        chinese = (String) rs.getObject("chinese");
                    }
                    JLabel label = new JLabel();
                    String text = pair.toString() + "     English: " + english + "     Chinese: " + chinese;
                    label.setText(text);
                    labels.add(label);
                    rs.close();
                }                
                statement.close();
                connection.close();
                pointer = 0;
                showSentences();
            } catch (SQLException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
