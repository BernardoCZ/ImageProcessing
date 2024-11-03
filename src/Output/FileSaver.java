package Output;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Alert.UserAlert;
import Input.FileSelector;

import java.io.*;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class FileSaver extends FileSelector{

    private JButton buttonSave;

    public FileSaver(int textFieldWidth, FileSelector fileSelector){
        super(textFieldWidth, false);

        createComponents();
        configComponents(null, fileSelector);
        addComponents();
    }

    public FileSaver(int textFieldWidth, FileNameExtensionFilter filter, FileSelector fileSelector){
        super(textFieldWidth, filter, false);

        createComponents();
        configComponents(filter, fileSelector);
        addComponents();

    }

    private void createComponents(){
        setButtonSave(new JButton("SALVAR"));
    }

    private void configComponents(FileNameExtensionFilter filter, FileSelector fileSelector){
        buttonSave.setBackground(new Color(107, 107, 107));
        buttonSave.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonSave.setFont(new Font(null, Font.BOLD, 12));
        buttonSave.setForeground(new Color(229,229,229));
        buttonSave.setMaximumSize(new Dimension(85, 40));
        buttonSave.addActionListener(event -> eventFileSave(event, filter, fileSelector));
        buttonSave.setFocusable(false);
    }

    private void addComponents(){
        add(buttonSave, BorderLayout.EAST);
    }

    private void eventFileSave(ActionEvent e, FileNameExtensionFilter filter, FileSelector fileSelector){
        String filePath = getFieldFile().getText();

        if(filePath.length() == 0){
            UserAlert userAlert = new UserAlert("Você não selecionou um destino!");
        }
        else if(fileSelector.getModifiedImageScreen() == null){
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");   
        }else{
            if(!getExtension(filePath).equals(Optional.of("jpg"))
                    && !getExtension(filePath).equals(Optional.of("jpeg"))){
                filePath += ".jpg";
            }
            try {
                File fileSaveCreate = new File(filePath);
                ImageIO.write(fileSelector.getModifiedImageScreen().getImg(), filter.getExtensions()[0], fileSaveCreate);
                UserAlert userAlert = new UserAlert("Imagem exportada com sucesso!"); 
            } catch (IOException ex) {
                UserAlert userAlert = new UserAlert("ERRO - Erro ao exportar imagem"); 
            }
        }
    }

    private Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public JButton getButtonSave() {
        return buttonSave;
    }
    public void setButtonSave(JButton buttonSave) {
        this.buttonSave = buttonSave;
    }
}
