/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencestatistics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author LeLe
 */
public class Classifier extends JFrame{
    private JButton next, previous,query,add,confirm;
    private JLabel chinese,english,lesson_id,sentence_id,new_tag;
    private JPanel tags;
    private JTextField lessonId, sentenceId,newTag;
    private Connection connection;
    private Statement en_zh, tag_list, sentence_tag;
    private ResultSet tagList,sentenceTag, EC;
    private ArrayList<String> old_tags = new ArrayList<>(), new_tags = new ArrayList<>();
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    
    public Classifier(String a) throws SQLException{
        super(a);
        initComponents();
        connectBase();
        initTags();
        displaySentence();
    }
    
    private void initComponents() {

        english = new javax.swing.JLabel();
        chinese = new javax.swing.JLabel();
        next = new javax.swing.JButton();
        previous = new javax.swing.JButton();
        lesson_id = new javax.swing.JLabel();
        lessonId = new javax.swing.JTextField();
        sentence_id = new javax.swing.JLabel();
        sentenceId = new javax.swing.JTextField();
        query = new javax.swing.JButton();
        new_tag = new javax.swing.JLabel();
        newTag = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        tags = new javax.swing.JPanel();
        confirm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        english.setText("English Sentence Here.");

        chinese.setText("Chinese Sentence Here.");

        next.setText("向后");
        next.addMouseListener(new NextMouseListener());

        previous.setText("向前");
        previous.addMouseListener(new PreviousMouseLestener());

        lesson_id.setText("课程编号");

        lessonId.setText("Lesson Id");

        sentence_id.setText("句子编号");

        sentenceId.setText("Sentence Id");

        query.setText("查询");
        query.addMouseListener(new QueryMouseListener());

        new_tag.setText("添加新标签");

        newTag.setText("New Tag");

        add.setText("添加");
        add.addMouseListener(new AddMouseListener());

        tags.setPreferredSize(new Dimension(600,400));
        tags.setLayout(new GridLayout(15,4));

        confirm.setText("确定");
        confirm.addMouseListener(new ConfirmMouseListener());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tags, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(previous, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(english, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                            .addComponent(chinese, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lesson_id)
                                .addGap(18, 18, 18)
                                .addComponent(lessonId, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sentence_id)
                                .addGap(18, 18, 18)
                                .addComponent(sentenceId, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(new_tag)
                                .addGap(18, 18, 18)
                                .addComponent(newTag)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(query, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addComponent(confirm, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(english, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chinese, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(previous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lesson_id, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lessonId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sentence_id, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sentenceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(query))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(new_tag, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(add)))
                    .addComponent(confirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tags, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
    }

    private void connectBase() throws SQLException{
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fiftylanguages?useUnicode=true&characterEncoding=utf-8", 
                "root", "0921");
        en_zh = connection.createStatement();
        tag_list = connection.createStatement();
        sentence_tag = connection.createStatement();
        tagList = tag_list.executeQuery("select * from tags");
        EC = en_zh.executeQuery("select * from chinese");
        
    }
    
    private void initTags() throws SQLException{
        while(tagList.next()){
            String oneTag = (String)tagList.getString("tag");
            JCheckBox checkbox = new JCheckBox(oneTag);
            checkbox.setPreferredSize(new Dimension(100,20));
            checkbox.addActionListener(new CheckBoxListener());
            checkBoxes.add(checkbox);
            tags.add(checkbox);
        }
        tags.revalidate();
        tags.repaint();
    }
    
    private void autoSelect() throws SQLException{
        String lineId = (String)EC.getObject("id");
        sentenceTag = sentence_tag.executeQuery("select * from sentence where sentence_id = \"" + lineId + "\"");
        while(sentenceTag.next()){
            String oneTag = (String)sentenceTag.getObject("tag");
            old_tags.add(oneTag);
            for(JCheckBox box : checkBoxes){
                if(oneTag.equals(box.getText())){
                    box.setSelected(true);
                    break;
                }
            }
        }
        
    }
    
    private void displaySentence() throws SQLException{
        DeSelect();
        old_tags.clear();
        new_tags.clear();
        if(EC.next()){
            String aboveE,belowC;
            aboveE = (String)EC.getObject("english");
            belowC = (String)EC.getObject("chinese");
            english.setText(aboveE);
            chinese.setText(belowC);
            autoSelect();
        }
    }

    private void DeSelect() {
        for(JCheckBox x : checkBoxes){
            x.setSelected(false);
        }
    }
    
    private class NextMouseListener implements MouseListener {

        public NextMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                displaySentence();
            } catch (SQLException ex) {
                Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
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

    private class PreviousMouseLestener implements MouseListener {

        public PreviousMouseLestener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int count = 2;
            try {
                while(count > 0 && ! EC.isBeforeFirst()){
                    EC.previous();
                    count --;
                }
                displaySentence();
            } catch (SQLException ex) {
                Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
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

    private class QueryMouseListener implements MouseListener {

        public QueryMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try{
                String lssnd = lessonId.getText();
                int num = Integer.parseInt(lssnd);
                if(0 < num && num <= 100){
                    EC = en_zh.executeQuery("select * from chinese where id like \"" + num + "%\"");
                }
            }catch(Exception ex){
                
            }
            try{
                String sntncd = sentenceId.getText();
                int num = Integer.parseInt(sntncd);
                if(0 < num && num <= 18){
                    EC.beforeFirst();
                    int count = 1;
                    while(count < num && EC.next()){
                        count ++;
                    }
                    displaySentence();
                }
            }catch(Exception ex){
                try {
                    EC.beforeFirst();
                    displaySentence();
                } catch (SQLException ex1) {
                    Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex1);
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

    private class AddMouseListener implements MouseListener {

        public AddMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            String tagtext = newTag.getText();
            JCheckBox newBox = new JCheckBox(tagtext);
            newBox.setPreferredSize(new Dimension(100,20));
            newBox.addActionListener(new CheckBoxListener());
            newBox.setSelected(true);
            tags.add(newBox);
            checkBoxes.add(newBox);
            tags.revalidate();
            tags.repaint();
            try {
                updateTag(tagtext);
            } catch (SQLException ex) {
                Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
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

        private void updateTag(String tagtext) throws SQLException {
            String command = "insert into tags (tag) value (\"" + tagtext + "\")";
            tag_list.execute(command);
        }
    }

    private class ConfirmMouseListener implements MouseListener {

        public ConfirmMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            for(String str : new_tags){
                if(old_tags.contains(str)){
                    old_tags.remove(str);
                    new_tags.remove(str);
                }
            }
            String sentenceKey = "";
            try {
                sentenceKey = (String)EC.getObject("id");
            } catch (SQLException ex) {
                Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(String str : new_tags){
                try {
                    insertTag(str,sentenceKey);
                } catch (SQLException ex) {
                    Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for(String str : old_tags){
                try {
                    removeTag(str,sentenceKey);
                } catch (SQLException ex) {
                    Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                displaySentence();
            } catch (SQLException ex) {
                Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
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

        private void insertTag(String str, String sentenceKey) throws SQLException {
            String command = "insert into sentence value(\"" + sentenceKey + "\",\"" + str + "\")";
            sentence_tag.execute(command);
        }

        private void removeTag(String str, String sentenceKey) throws SQLException {
            String command = "delete from sentence where sentence_id = \"" + sentenceKey + "\" and tag = \"" + str + "\"";
            sentence_tag.execute(command);
        }
    }

    private class CheckBoxListener implements ActionListener {

        public CheckBoxListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox source = (JCheckBox)e.getSource();
            String tagContent = source.getText();
            if(source.isSelected()){
                new_tags.add(tagContent);
            }else{
                new_tags.remove(tagContent);
            }
        }
    }
    
}
