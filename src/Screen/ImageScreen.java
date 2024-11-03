package Screen;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Alert.UserAlert;

public class ImageScreen extends Screen{

    BufferedImage img;
    private JLabel imgLabel;

    public ImageScreen(String filePath){

        super("Imagem Base");

        try {                
            setImg(ImageIO.read(new File(filePath)));
        } catch (IOException ex) {
            UserAlert userAlert = new UserAlert("ERRO - Erro ao carregar imagem");
        }

        setLocation(350, 0);
        setLayout(new BorderLayout());

        createComponents();
        configComponents();
        addComponents();
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public ImageScreen(BufferedImage img, String title){

        super(title);

        setImg(img);

        setLocation(350, 50);
        setLayout(new BorderLayout());

        createComponents(title);
        configComponents();
        addComponents();
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void createComponents(){

        setTitle("Imagem Base");     

        setImgLabel(new JLabel(new ImageIcon(getImg())));
    }

    private void createComponents(String title){

        setTitle(title);     

        setImgLabel(new JLabel(new ImageIcon(getImg())));
    }

    private void configComponents(){
        imgLabel.setBounds(0, 0, getImg().getWidth(), getImg().getHeight());
    }

    private void addComponents(){

        add(imgLabel);

    }

    public void reloadImg(){

        remove(imgLabel);

        setImgLabel(new JLabel(new ImageIcon(getImg())));

        configComponents();
        addComponents();

        update(getGraphics());

    }

    public BufferedImage getImg() {
        return img;
    }
    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public JLabel getImgLabel() {
        return imgLabel;
    }
    public void setImgLabel(JLabel imgLabel) {
        this.imgLabel = imgLabel;
    }

}
