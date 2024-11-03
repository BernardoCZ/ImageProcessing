package Screen;

import java.awt.*;
import javax.swing.*;
import Content.ContentHomeScreen;

public class HomeScreen extends Screen{

    private JScrollPane scrollPane;
    private ContentHomeScreen content;

    public HomeScreen(){

        super("Home Screen");
        setLocation(0, 0);
        setSize(360, 720);
        
        createComponents();
        configComponents();
        addComponents();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createComponents(){
        setTitle("Processador de Imagens");
        setContent(new ContentHomeScreen());
        setScrollPane(new JScrollPane(content));
    }

    private void configComponents(){
        scrollPane.setBounds(0, 0, 345, 685);
        scrollPane.setBackground(new Color(76,76,76));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void addComponents() {
        add(scrollPane);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public ContentHomeScreen getContent() {
        return content;
    }
    public void setContent(ContentHomeScreen content) {
        this.content = content;
    }

}
