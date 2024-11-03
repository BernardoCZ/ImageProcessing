package Input;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Alert.UserAlert;
import ImageProcessing.ImageProcessing;
import Input.FileSelector;

import java.io.*;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class HistogramMatchingFileSelector extends FileSelector{

    private JButton buttonApply;

    public HistogramMatchingFileSelector(int textFieldWidth, FileSelector fileSelector){
        super(textFieldWidth, false);

        createComponents();
        configComponents(null, fileSelector);
        addComponents();
    }

    public HistogramMatchingFileSelector(int textFieldWidth, FileNameExtensionFilter filter, FileSelector fileSelector){
        super(textFieldWidth, filter, false);

        createComponents();
        configComponents(filter, fileSelector);
        addComponents();

    }

    private void createComponents(){
        setButtonApply(new JButton("APLICAR"));
    }

    private void configComponents(FileNameExtensionFilter filter, FileSelector fileSelector){
        buttonApply.setBackground(new Color(107, 107, 107));
        buttonApply.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonApply.setFont(new Font(null, Font.BOLD, 12));
        buttonApply.setForeground(new Color(229,229,229));
        buttonApply.setMaximumSize(new Dimension(85, 40));
        buttonApply.setFocusable(false);

        buttonApply.addActionListener(event -> ImageProcessing.histogramMatching(fileSelector, getFieldFile().getText()));
    }

    private void addComponents(){
        add(buttonApply, BorderLayout.EAST);
    }

    public JButton getButtonApply() {
        return buttonApply;
    }
    public void setButtonApply(JButton buttonApply) {
        this.buttonApply = buttonApply;
    }
}
