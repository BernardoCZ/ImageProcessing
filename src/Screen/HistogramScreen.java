package Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Alert.UserAlert;
import Content.Histogram;

public class HistogramScreen extends Screen{

    BufferedImage img;
    private Histogram histogram;

    public HistogramScreen(BufferedImage img, String screenTitle)
    {

        super(screenTitle);

        setLocation(350, 450);
        setLayout(new BorderLayout());

        createComponents(img, screenTitle);
        configComponents();
        addComponents();
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public HistogramScreen(BufferedImage img, String screenTitle, boolean sndHist)
    {

        super(screenTitle);

        setLocation(1200, 450);
        setLayout(new BorderLayout());

        createComponents(img, screenTitle);
        configComponents();
        addComponents();
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public HistogramScreen(Histogram h, String screenTitle)
    {

        super(screenTitle);

        setLocation(350, 450);
        setLayout(new BorderLayout());

        setHistogram(h);

        createComponents(h, screenTitle);
        configComponents();
        addComponents();
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void createComponents(BufferedImage img, String screenTitle){

        setTitle(screenTitle);     

        setHistogram(new Histogram(img));
    }

    private void createComponents(Histogram h, String screenTitle){

        setTitle(screenTitle);     

        setHistogram(h);
    }

    private void configComponents(){
        histogram.setBounds(0, 0, 1000, 500);
        histogram.setBackground(new Color(68,68,68));
    }

    private void addComponents(){

        add(histogram);

    }

    public Histogram getHistogram() {
        return histogram;
    }
    public void setHistogram(Histogram histogram) {
        this.histogram = histogram;
    }
}
