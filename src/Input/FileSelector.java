package Input;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Alert.UserAlert;
import Screen.*;

public class FileSelector extends JPanel{

    private JButton         buttonFile;
    private JTextField      fieldFile;
    private JFileChooser    windowFile;
    private String          fileContent;
    private File            file;

    private ImageScreen BaseImageScreen = null;
    private ModifiedImageScreen ModifiedImageScreen = null;

    // Campo com tamanho e filtro
    public FileSelector(int textFieldWidth, FileNameExtensionFilter filter, Boolean mainimporter){
        initializeFileSelector(textFieldWidth, filter);

        buttonFile.addActionListener(event -> selectFile(event, mainimporter));
    }

    // Campo com tamanho
    public FileSelector(int textFieldWidth, Boolean mainimporter){
        initializeFileSelector(textFieldWidth, null);

        buttonFile.addActionListener(event -> selectFile(event, mainimporter));
    }


    private void initializeFileSelector (int textFieldWidth, FileNameExtensionFilter filter){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        ImageIcon imageIcon = new ImageIcon("img/fileIcon.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  

        setButtonFile(new JButton("", new ImageIcon(newimg)));
        setFieldFile(new JTextField());
        
        buttonFile.setBackground(new Color(107, 107, 107));
        buttonFile.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonFile.setMaximumSize(new Dimension(60, 40));
        buttonFile.setMinimumSize(new Dimension(80, 40));
        buttonFile.setFocusable(false);

        fieldFile.setFont(new Font(null, Font.BOLD, 12));
        fieldFile.setBackground(new Color(68,68,68));
        fieldFile.setBorder(new LineBorder(new Color(68,68,68),10));
        fieldFile.setMaximumSize(new Dimension(textFieldWidth, 40));
        fieldFile.setEditable(false);
        fieldFile.setFocusable(false);
        fieldFile.setForeground(new Color(204, 204, 204));

        
        windowFile  = new JFileChooser();
        windowFile.setVisible(false);
        
        windowFile.setAcceptAllFileFilterUsed(false);
        
        if(filter != null){
            windowFile.addChoosableFileFilter(filter);
        }
        
        add(buttonFile,  BorderLayout.WEST);
        add(fieldFile,  BorderLayout.EAST);
        add(windowFile);
    }

    private void selectFile(ActionEvent e, Boolean mainimporter){

        windowFile.setVisible(true);
        int windowState = windowFile.showOpenDialog(null);

        if (windowState == JFileChooser.APPROVE_OPTION){

            // Imprime no field relacionado ao seletor o caminho do arquivo selecionado
            fieldFile.setText(windowFile.getSelectedFile().getPath());

            if(mainimporter)
            {
                if(fieldFile.getText().equals("")){
                    UserAlert userAlert = new UserAlert("ERRO - Imagem não selecionada");
                }else{
                    Screen.closeOldScreens("Imagem Base");
                    Screen.closeOldScreens("Imagem Modificada");
                    Screen.closeOldDefinedScreens();
                    setBaseImageScreen(new ImageScreen(fieldFile.getText()));
                    setModifiedImageScreen(new ModifiedImageScreen(fieldFile.getText()));
                }
            }
        }
    }

    public JButton getButtonFile() {
        return buttonFile;
    }
    public void setButtonFile(JButton buttonFile) {
        this.buttonFile = buttonFile;
    }


    public JTextField getFieldFile() {
        return fieldFile;
    }
    public void setFieldFile(JTextField fieldFile) {
        this.fieldFile = fieldFile;
    }


    public JFileChooser getWindowFile() {
        return windowFile;
    }
    public void setWindowFile(JFileChooser windowFile) {
        this.windowFile = windowFile;
    }


    public String getFileContent() {
        return fileContent;
    }
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }


    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public ImageScreen getBaseImageScreen() {
        return BaseImageScreen;
    }
    public void setBaseImageScreen(ImageScreen baseImageScreen) {
        BaseImageScreen = baseImageScreen;
    }

    public ModifiedImageScreen getModifiedImageScreen() {
        return ModifiedImageScreen;
    }
    public void setModifiedImageScreen(ModifiedImageScreen modifiedImageScreen) {
        ModifiedImageScreen = modifiedImageScreen;
    }

}