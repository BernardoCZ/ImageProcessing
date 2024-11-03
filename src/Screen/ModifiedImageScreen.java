package Screen;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Alert.UserAlert;

public class ModifiedImageScreen extends Screen{

    BufferedImage img;
    private JLabel imgLabel;

    boolean isHorizontallyFlipped = false;
    boolean isVerticallyFlipped = false;

    public ModifiedImageScreen(String filePath){

        super("Imagem Modificada");

        try {                
            setImg(ImageIO.read(new File(filePath)));
        } catch (IOException ex) {
            UserAlert userAlert = new UserAlert("ERRO - Erro ao carregar imagem");
        }

        setLocation(370 + getImg().getWidth(), 0);
        setLayout(new BorderLayout());

        createComponents(filePath);
        configComponents();
        addComponents();
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void createComponents(String filePath){

        setTitle("Imagem Modificada");     

        setImgLabel(new JLabel(new ImageIcon(getImg())));
    }

    private void configComponents(){
        imgLabel.setBounds(0, 0, getImg().getWidth(), getImg().getHeight());

        setSize(getImg().getWidth()+17, getImg().getHeight()+40);
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

    public boolean isHorizontallyFlipped() {
        return isHorizontallyFlipped;
    }
    public void setHorizontallyFlipped(boolean isHorizontallyFlipped) {
        this.isHorizontallyFlipped = isHorizontallyFlipped;
    }
    
    public boolean isVerticallyFlipped() {
        return isVerticallyFlipped;
    }
    public void setVerticallyFlipped(boolean isVerticallyFlipped) {
        this.isVerticallyFlipped = isVerticallyFlipped;
    }

}
