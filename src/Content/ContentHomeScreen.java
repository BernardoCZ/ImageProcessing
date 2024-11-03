package Content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;

import Alert.UserAlert;
import ImageProcessing.ImageProcessing;
import Input.FileSelector;
import Input.HistogramMatchingFileSelector;
import Output.FileSaver;

public class ContentHomeScreen extends JPanel {

    private JLabel labelFileSelector;
    private FileSelector fileSelector;

    private JLabel labelFileSave;
    private FileSaver fileSave;

    private JButton buttonClear;
    private JButton buttonFlipHorizontal;
    private JButton buttonFlipVertical;
    private JButton buttonCounterclockwiseRotation;
    private JButton buttonClockwiseRotation;
    private JButton buttonHistogram;
    private JButton buttonHistogramEqualization;
    private JButton buttonGrayscale;
    private JButton buttonNegative;
    private JButton buttonZoomIn;

    private JLabel labelQuantization;
    private JFormattedTextField quantizationInput;
    private JButton buttonQuantization;

    private JLabel labelBrightness;
    private JFormattedTextField brightnessInput;
    private JButton buttonBrightnessInc;
    private JButton buttonBrightnessDec;

    private JLabel labelContrast;
    private JFormattedTextField contrastInput;
    private JButton buttonContrast;

    private JLabel labelZoomOut;
    private JFormattedTextField zoomOutSxInput;
    private JFormattedTextField zoomOutSyInput;
    private JButton buttonZoomOut;

    private JLabel labelFilter;
    private JButton buttonGaussian;
    private JButton buttonLaplacian;
    private JButton buttonGenericHighPass;
    private JButton buttonPrewittHx;
    private JButton buttonPrewittHy;
    private JButton buttonSobelHx;
    private JButton buttonSobelHy;

    private JFormattedTextField matrixFilter_1_1;
    private JFormattedTextField matrixFilter_1_2;
    private JFormattedTextField matrixFilter_1_3;
    private JFormattedTextField matrixFilter_2_1;
    private JFormattedTextField matrixFilter_2_2;
    private JFormattedTextField matrixFilter_2_3;
    private JFormattedTextField matrixFilter_3_1;
    private JFormattedTextField matrixFilter_3_2;
    private JFormattedTextField matrixFilter_3_3;
    private JButton buttonApplyFilter;

    private JLabel labelHistogramMatching;
    private HistogramMatchingFileSelector histogramMatchingInput;

    public ContentHomeScreen(){

        setLayout(null);
        setPreferredSize(new Dimension(350, 1140));
        setBackground(new Color(76,76,76));

        UIManager.put("ToolTip.background", new ColorUIResource(107, 107, 107));
        UIManager.put("ToolTip.foreground", new ColorUIResource(229,229,229));
        UIManager.put("ToolTip.border", new LineBorder(new Color(68,68,68),2));
        UIManager.put("ToolTip.font", new Font(null, Font.BOLD, 12));
        
        createComponents();
        configComponents();
        addComponents();

    }


    private void createComponents(){


        setLabelFileSelector(new JLabel("IMPORTAR"));
        setFileSelector(new FileSelector(230, new FileNameExtensionFilter(".jpg,.jpeg", "jpg", "jpeg"), true));

        //---------------------------------------------------------

        setLabelFileSave(new JLabel("EXPORTAR"));               
        setFileSave(new FileSaver(145, new FileNameExtensionFilter(".jpg,.jpeg", "jpg", "jpeg"), getFileSelector()));                    
        
        //---------------------------------------------------------

        ImageIcon clearIcon = new ImageIcon("img/clearIcon.png");
        Image clearIconImg = clearIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonClear(new JButton("", new ImageIcon(clearIconImg)));

        //---------------------------------------------------------

        ImageIcon flipHorizontalIcon = new ImageIcon("img/flipHorizontalIcon.png");
        Image flipHorizontalIconImg = flipHorizontalIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonFlipHorizontal(new JButton("", new ImageIcon(flipHorizontalIconImg)));

        //---------------------------------------------------------

        ImageIcon flipVerticalIcon = new ImageIcon("img/flipVerticalIcon.png");
        Image flipVerticalIconImg = flipVerticalIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonFlipVertical(new JButton("", new ImageIcon(flipVerticalIconImg)));

        //---------------------------------------------------------

        ImageIcon counterclockwiseRotationIcon = new ImageIcon("img/counterclockwiseRotationIcon.png");
        Image counterclockwiseRotationIconImg = counterclockwiseRotationIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonCounterclockwiseRotation(new JButton("", new ImageIcon(counterclockwiseRotationIconImg)));

        //---------------------------------------------------------

        ImageIcon clockwiseRotationIcon = new ImageIcon("img/clockwiseRotationIcon.png");
        Image clockwiseRotationIconImg = clockwiseRotationIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonClockwiseRotation(new JButton("", new ImageIcon(clockwiseRotationIconImg)));

        //---------------------------------------------------------

        ImageIcon histogramIcon = new ImageIcon("img/histogramIcon.png");
        Image histogramIconImg = histogramIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonHistogram(new JButton("", new ImageIcon(histogramIconImg)));

        //---------------------------------------------------------

        ImageIcon histogramEqualizationIcon = new ImageIcon("img/histogramEqualizationIcon.png");
        Image histogramEqualizationIconImg = histogramEqualizationIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonHistogramEqualization(new JButton("", new ImageIcon(histogramEqualizationIconImg)));

        //---------------------------------------------------------

        ImageIcon grayscaleIcon = new ImageIcon("img/grayscaleIcon.png");
        Image grayscaleIconImg = grayscaleIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonGrayscale(new JButton("", new ImageIcon(grayscaleIconImg)));

        //---------------------------------------------------------

        ImageIcon negativeIcon = new ImageIcon("img/negativeIcon.png");
        Image negativeIconImg = negativeIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonNegative(new JButton("", new ImageIcon(negativeIconImg)));

        //---------------------------------------------------------

        ImageIcon zoomInIcon = new ImageIcon("img/zoomInIcon.png");
        Image zoomInIconImg = zoomInIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonZoomIn(new JButton("", new ImageIcon(zoomInIconImg)));

        //---------------------------------------------------------

        setLabelQuantization(new JLabel("QUANTIZAÇÃO"));

        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format){
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.length() == 0)
                    return null;
                return super.stringToValue(text);
            }
        };
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(255);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        setQuantizationInput(new JFormattedTextField(formatter));

        setButtonQuantization(new JButton("APLICAR"));

        //---------------------------------------------------------

        setLabelBrightness(new JLabel("BRILHO"));

        setBrightnessInput(new JFormattedTextField(formatter));

        ImageIcon plusIcon = new ImageIcon("img/plusIcon.png");
        Image plusIconImg = plusIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonBrightnessInc(new JButton("", new ImageIcon(plusIconImg)));

        ImageIcon minusIcon = new ImageIcon("img/minusIcon.png");
        Image minusIconImg = minusIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        
        setButtonBrightnessDec(new JButton("", new ImageIcon(minusIconImg)));

        //---------------------------------------------------------

        setLabelContrast(new JLabel("CONTRASTE"));

        final JFormattedTextField textFieldDecimalPositive = new JFormattedTextField();
        textFieldDecimalPositive.setFormatterFactory(new AbstractFormatterFactory() {

            @Override
            public AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(6);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                formatter.setMinimum(0.00);
                formatter.setMaximum(255.00);
                return formatter;
            }
        });

        setContrastInput(textFieldDecimalPositive);

        setButtonContrast(new JButton("APLICAR"));

        //---------------------------------------------------------

        setLabelZoomOut(new JLabel("REDUZIR IMAGEM (ZOOM OUT)"));

        NumberFormat formatZoomOut = NumberFormat.getInstance();
        formatZoomOut.setGroupingUsed(false);
        NumberFormatter formatterZoomOut = new NumberFormatter(format){
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.length() == 0)
                    return null;
                return super.stringToValue(text);
            }
        };
        formatterZoomOut.setValueClass(Integer.class);
        formatterZoomOut.setMinimum(1);
        formatterZoomOut.setAllowsInvalid(false);
        formatterZoomOut.setCommitsOnValidEdit(true);

        setZoomOutSxInput(new JFormattedTextField(formatterZoomOut));
        getZoomOutSxInput().setValue(1);
        setZoomOutSyInput(new JFormattedTextField(formatterZoomOut));
        getZoomOutSyInput().setValue(1);

        setButtonZoomOut(new JButton("APLICAR FATORES Sx E Sy"));

        //---------------------------------------------------------

        setLabelFilter(new JLabel("FILTROS"));

        setButtonGaussian(new JButton("GAUSSIANO"));
        setButtonLaplacian(new JButton("LAPLACIANO"));
        setButtonGenericHighPass(new JButton("PASSA ALTAS GENÉRICO"));
        setButtonPrewittHx(new JButton("PREWITT HX"));
        setButtonPrewittHy(new JButton("PREWITT HY"));
        setButtonSobelHx(new JButton("SOBEL HX"));
        setButtonSobelHy(new JButton("SOBEL HY"));

        setMatrixFilter_1_1(new JFormattedTextField("0.0"));
        setMatrixFilter_1_2(new JFormattedTextField("0.0"));
        setMatrixFilter_1_3(new JFormattedTextField("0.0"));
        setMatrixFilter_2_1(new JFormattedTextField("0.0"));
        setMatrixFilter_2_2(new JFormattedTextField("1.0"));
        setMatrixFilter_2_3(new JFormattedTextField("0.0"));
        setMatrixFilter_3_1(new JFormattedTextField("0.0"));
        setMatrixFilter_3_2(new JFormattedTextField("0.0"));
        setMatrixFilter_3_3(new JFormattedTextField("0.0"));

        setButtonApplyFilter(new JButton("APLICAR FILTRO PERSONALIZADO"));

        //---------------------------------------------------------

        setLabelHistogramMatching(new JLabel("HISTOGRAM MATCHING (TONS DE CINZA)"));
        setHistogramMatchingInput(new HistogramMatchingFileSelector(145, new FileNameExtensionFilter(".jpg,.jpeg", "jpg", "jpeg"), getFileSelector()));

    }




    private void configComponents(){

        labelFileSelector.setBounds(20, 0, 200, 50);
        labelFileSelector.setFont(new Font(null, Font.BOLD, 12));
        labelFileSelector.setForeground(new Color(229,229,229));

        fileSelector.setBounds(20, 40, 295, 40);
        fileSelector.setBackground(new Color(76,76,76));

        //---------------------------------------------------------

        labelFileSave.setBounds(20, 80, 200, 50);
        labelFileSave.setFont(new Font(null, Font.BOLD, 12));
        labelFileSave.setForeground(new Color(229,229,229));

        fileSave.setBounds(20, 120, 500, 40);
        fileSave.setBackground(new Color(76,76,76));

        //---------------------------------------------------------

        buttonClear.setBounds(20, 180, 50, 50);
        buttonClear.setBackground(new Color(107, 107, 107));
        buttonClear.setFont(new Font(null, Font.BOLD, 18));
        buttonClear.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonClear.setForeground(new Color(229,229,229));
        buttonClear.setToolTipText("Descartar Alterações");

        buttonClear.addActionListener(event -> ImageProcessing.clear(getFileSelector()));
        
        //---------------------------------------------------------

        buttonFlipHorizontal.setBounds(80, 180, 50, 50);
        buttonFlipHorizontal.setBackground(new Color(107, 107, 107));
        buttonFlipHorizontal.setFont(new Font(null, Font.BOLD, 18));
        buttonFlipHorizontal.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonFlipHorizontal.setForeground(new Color(229,229,229));
        buttonFlipHorizontal.setToolTipText("Espelhamento Horizontal");

        buttonFlipHorizontal.addActionListener(event -> ImageProcessing.eventFlipHorizontal(getFileSelector()));

        //---------------------------------------------------------

        buttonFlipVertical.setBounds(140, 180, 50, 50);
        buttonFlipVertical.setBackground(new Color(107, 107, 107));
        buttonFlipVertical.setFont(new Font(null, Font.BOLD, 18));
        buttonFlipVertical.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonFlipVertical.setForeground(new Color(229,229,229));
        buttonFlipVertical.setToolTipText("Espelhamento Vertical");

        buttonFlipVertical.addActionListener(event -> ImageProcessing.eventFlipVertical(getFileSelector()));

        //---------------------------------------------------------

        buttonCounterclockwiseRotation.setBounds(200, 180, 50, 50);
        buttonCounterclockwiseRotation.setBackground(new Color(107, 107, 107));
        buttonCounterclockwiseRotation.setFont(new Font(null, Font.BOLD, 18));
        buttonCounterclockwiseRotation.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonCounterclockwiseRotation.setForeground(new Color(229,229,229));
        buttonCounterclockwiseRotation.setToolTipText("Rotacionar 90º no Sentido Anti-Horário");

        buttonCounterclockwiseRotation.addActionListener(event -> ImageProcessing.rotation90(getFileSelector(), false));

        //---------------------------------------------------------

        buttonClockwiseRotation.setBounds(260, 180, 50, 50);
        buttonClockwiseRotation.setBackground(new Color(107, 107, 107));
        buttonClockwiseRotation.setFont(new Font(null, Font.BOLD, 18));
        buttonClockwiseRotation.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonClockwiseRotation.setForeground(new Color(229,229,229));
        buttonClockwiseRotation.setToolTipText("Rotacionar 90º no Sentido Horário");

        buttonClockwiseRotation.addActionListener(event -> ImageProcessing.rotation90(getFileSelector(), true));

        //---------------------------------------------------------

        buttonHistogram.setBounds(20, 240, 50, 50);
        buttonHistogram.setBackground(new Color(107, 107, 107));
        buttonHistogram.setFont(new Font(null, Font.BOLD, 18));
        buttonHistogram.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonHistogram.setForeground(new Color(229,229,229));
        buttonHistogram.setToolTipText("Exibir Histograma da Imagem (Tons de Cinza)");

        buttonHistogram.addActionListener(event -> ImageProcessing.histogram(getFileSelector()));

        //---------------------------------------------------------

        buttonHistogramEqualization.setBounds(80, 240, 50, 50);
        buttonHistogramEqualization.setBackground(new Color(107, 107, 107));
        buttonHistogramEqualization.setFont(new Font(null, Font.BOLD, 18));
        buttonHistogramEqualization.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonHistogramEqualization.setForeground(new Color(229,229,229));
        buttonHistogramEqualization.setToolTipText("Equalizar Histograma da Imagem (Tons de Cinza)");

        buttonHistogramEqualization.addActionListener(event -> ImageProcessing.histogramEqualization(getFileSelector()));

        //---------------------------------------------------------

        buttonGrayscale.setBounds(140, 240, 50, 50);
        buttonGrayscale.setBackground(new Color(107, 107, 107));
        buttonGrayscale.setFont(new Font(null, Font.BOLD, 18));
        buttonGrayscale.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonGrayscale.setForeground(new Color(229,229,229));
        buttonGrayscale.setToolTipText("Converter para Escala de Cinza (Luminância)");

        buttonGrayscale.addActionListener(event -> ImageProcessing.eventGrayscale(getFileSelector()));

        //---------------------------------------------------------

        buttonNegative.setBounds(200, 240, 50, 50);
        buttonNegative.setBackground(new Color(107, 107, 107));
        buttonNegative.setFont(new Font(null, Font.BOLD, 18));
        buttonNegative.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonNegative.setForeground(new Color(229,229,229));
        buttonNegative.setToolTipText("Negativo da Imagem");

        buttonNegative.addActionListener(event -> ImageProcessing.negative(getFileSelector()));

        //---------------------------------------------------------

        buttonZoomIn.setBounds(260, 240, 50, 50);
        buttonZoomIn.setBackground(new Color(107, 107, 107));
        buttonZoomIn.setFont(new Font(null, Font.BOLD, 18));
        buttonZoomIn.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonZoomIn.setForeground(new Color(229,229,229));
        buttonZoomIn.setToolTipText("Ampliar Imagem 2x (Zoom In)");

        buttonZoomIn.addActionListener(event -> ImageProcessing.zoomIn2x2(getFileSelector()));

        //---------------------------------------------------------

        labelQuantization.setBounds(20, 290, 200, 50);
        labelQuantization.setFont(new Font(null, Font.BOLD, 12));
        labelQuantization.setForeground(new Color(229,229,229));

        quantizationInput.setBounds(20, 330, 205, 40);
        quantizationInput.setFont(new Font(null, Font.BOLD, 12));
        quantizationInput.setBackground(new Color(68,68,68));
        quantizationInput.setBorder(new LineBorder(new Color(68,68,68),10));
        quantizationInput.setForeground(new Color(204, 204, 204));
        quantizationInput.setCaretColor(new Color(204, 204, 204));

        buttonQuantization.setBounds(225, 330, 85, 40);
        buttonQuantization.setBackground(new Color(107, 107, 107));
        buttonQuantization.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonQuantization.setFont(new Font(null, Font.BOLD, 12));
        buttonQuantization.setForeground(new Color(229,229,229));
        buttonQuantization.setFocusable(false);
        buttonQuantization.setToolTipText("Quantizar Imagem Base");

        buttonQuantization.addActionListener(event -> ImageProcessing.quantization(getQuantizationInput().getText(), getFileSelector()));

        //---------------------------------------------------------

        labelBrightness.setBounds(20, 370, 200, 50);
        labelBrightness.setFont(new Font(null, Font.BOLD, 12));
        labelBrightness.setForeground(new Color(229,229,229));

        brightnessInput.setBounds(20, 410, 209, 40);
        brightnessInput.setFont(new Font(null, Font.BOLD, 12));
        brightnessInput.setBackground(new Color(68,68,68));
        brightnessInput.setBorder(new LineBorder(new Color(68,68,68),10));
        brightnessInput.setForeground(new Color(204, 204, 204));
        brightnessInput.setCaretColor(new Color(204, 204, 204));

        buttonBrightnessInc.setBounds(229, 410, 40, 40);
        buttonBrightnessInc.setBackground(new Color(107, 107, 107));
        buttonBrightnessInc.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonBrightnessInc.setFont(new Font(null, Font.BOLD, 20));
        buttonBrightnessInc.setForeground(new Color(229,229,229));
        buttonBrightnessInc.setFocusable(false);
        buttonBrightnessInc.setToolTipText("Aumentar Brilho");

        buttonBrightnessInc.addActionListener(event -> ImageProcessing.brightness(getBrightnessInput().getText(), true, getFileSelector()));

        buttonBrightnessDec.setBounds(270, 410, 40, 40);
        buttonBrightnessDec.setBackground(new Color(107, 107, 107));
        buttonBrightnessDec.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonBrightnessDec.setFont(new Font(null, Font.BOLD, 20));
        buttonBrightnessDec.setForeground(new Color(229,229,229));
        buttonBrightnessDec.setFocusable(false);
        buttonBrightnessDec.setToolTipText("Diminuir Brilho");

        buttonBrightnessDec.addActionListener(event -> ImageProcessing.brightness(getBrightnessInput().getText(), false, getFileSelector()));

        //---------------------------------------------------------

        labelContrast.setBounds(20, 450, 200, 50);
        labelContrast.setFont(new Font(null, Font.BOLD, 12));
        labelContrast.setForeground(new Color(229,229,229));

        contrastInput.setBounds(20, 490, 205, 40);
        contrastInput.setFont(new Font(null, Font.BOLD, 12));
        contrastInput.setBackground(new Color(68,68,68));
        contrastInput.setBorder(new LineBorder(new Color(68,68,68),10));
        contrastInput.setForeground(new Color(204, 204, 204));
        contrastInput.setCaretColor(new Color(204, 204, 204));

        buttonContrast.setBounds(225, 490, 85, 40);
        buttonContrast.setBackground(new Color(107, 107, 107));
        buttonContrast.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonContrast.setFont(new Font(null, Font.BOLD, 12));
        buttonContrast.setForeground(new Color(229,229,229));
        buttonContrast.setFocusable(false);
        buttonContrast.setToolTipText("Aplicar Contraste");

        buttonContrast.addActionListener(event -> ImageProcessing.contrast(getContrastInput().getText(), getFileSelector()));

        //---------------------------------------------------------

        labelZoomOut.setBounds(20, 530, 200, 50);
        labelZoomOut.setFont(new Font(null, Font.BOLD, 12));
        labelZoomOut.setForeground(new Color(229,229,229));

        zoomOutSxInput.setBounds(20, 570, 40, 40);
        zoomOutSxInput.setFont(new Font(null, Font.BOLD, 12));
        zoomOutSxInput.setBackground(new Color(68,68,68));
        zoomOutSxInput.setBorder(new LineBorder(new Color(68,68,68),10));
        zoomOutSxInput.setForeground(new Color(204, 204, 204));
        zoomOutSxInput.setCaretColor(new Color(204, 204, 204));

        zoomOutSyInput.setBounds(70, 570, 40, 40);
        zoomOutSyInput.setFont(new Font(null, Font.BOLD, 12));
        zoomOutSyInput.setBackground(new Color(68,68,68));
        zoomOutSyInput.setBorder(new LineBorder(new Color(68,68,68),10));
        zoomOutSyInput.setForeground(new Color(204, 204, 204));
        zoomOutSyInput.setCaretColor(new Color(204, 204, 204));

        buttonZoomOut.setBounds(120, 570, 190, 40);
        buttonZoomOut.setBackground(new Color(107, 107, 107));
        buttonZoomOut.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonZoomOut.setFont(new Font(null, Font.BOLD, 12));
        buttonZoomOut.setForeground(new Color(229,229,229));
        buttonZoomOut.setFocusable(false);
        buttonZoomOut.setToolTipText("Aplicar Redução (Zoom Out)");

        buttonZoomOut.addActionListener(event -> ImageProcessing.zoomOut(getFileSelector(), getZoomOutSxInput().getText(), getZoomOutSyInput().getText()));

        //---------------------------------------------------------

        labelFilter.setBounds(20, 610, 200, 50);
        labelFilter.setFont(new Font(null, Font.BOLD, 12));
        labelFilter.setForeground(new Color(229,229,229));

        //---------------------------------------------------------

        buttonGaussian.setBounds(20, 650, 140, 40);
        buttonGaussian.setBackground(new Color(107, 107, 107));
        buttonGaussian.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonGaussian.setFont(new Font(null, Font.BOLD, 12));
        buttonGaussian.setForeground(new Color(229,229,229));
        buttonGaussian.setFocusable(false);
        buttonGaussian.setToolTipText("Filtro Gaussiano (Produz Borramento)");

        buttonGaussian.addActionListener(event -> ImageProcessing.gaussianFilter(getFileSelector()));

        buttonLaplacian.setBounds(170, 650, 140, 40);
        buttonLaplacian.setBackground(new Color(107, 107, 107));
        buttonLaplacian.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonLaplacian.setFont(new Font(null, Font.BOLD, 12));
        buttonLaplacian.setForeground(new Color(229,229,229));
        buttonLaplacian.setFocusable(false);
        buttonLaplacian.setToolTipText("Filtro Laplaciano (Detecta Arestas Importantes)");

        buttonLaplacian.addActionListener(event -> ImageProcessing.highPassFilter(getFileSelector(), false));

        //---------------------------------------------------------

        buttonGenericHighPass.setBounds(20, 700, 290, 40);
        buttonGenericHighPass.setBackground(new Color(107, 107, 107));
        buttonGenericHighPass.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonGenericHighPass.setFont(new Font(null, Font.BOLD, 12));
        buttonGenericHighPass.setForeground(new Color(229,229,229));
        buttonGenericHighPass.setFocusable(false);
        buttonGenericHighPass.setToolTipText("Filtro Passa Altas Genérico (Detector de Arestas Mais Sensitivo)");

        buttonGenericHighPass.addActionListener(event -> ImageProcessing.highPassFilter(getFileSelector(), true));

        //---------------------------------------------------------

        buttonPrewittHx.setBounds(20, 750, 140, 40);
        buttonPrewittHx.setBackground(new Color(107, 107, 107));
        buttonPrewittHx.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonPrewittHx.setFont(new Font(null, Font.BOLD, 12));
        buttonPrewittHx.setForeground(new Color(229,229,229));
        buttonPrewittHx.setFocusable(false);
        buttonPrewittHx.setToolTipText("Prewitt Hx (Gradiente dos Tons de Cinza na Direção Horizontal – Efeito de Relevo)");

        buttonPrewittHx.addActionListener(event -> ImageProcessing.prewittFilter(getFileSelector(), true));

        buttonPrewittHy.setBounds(170, 750, 140, 40);
        buttonPrewittHy.setBackground(new Color(107, 107, 107));
        buttonPrewittHy.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonPrewittHy.setFont(new Font(null, Font.BOLD, 12));
        buttonPrewittHy.setForeground(new Color(229,229,229));
        buttonPrewittHy.setFocusable(false);
        buttonPrewittHy.setToolTipText("Prewitt Hy (Gradiente dos Tons de Cinza na Direção Vertical – Efeito de Relevo)");

        buttonPrewittHy.addActionListener(event -> ImageProcessing.prewittFilter(getFileSelector(), false));

        //---------------------------------------------------------

        buttonSobelHx.setBounds(20, 800, 140, 40);
        buttonSobelHx.setBackground(new Color(107, 107, 107));
        buttonSobelHx.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonSobelHx.setFont(new Font(null, Font.BOLD, 12));
        buttonSobelHx.setForeground(new Color(229,229,229));
        buttonSobelHx.setFocusable(false);
        buttonSobelHx.setToolTipText("Sobel Hx (Mais Sensível ao Gradiente dos Tons de Cinza na Direção Horizontal – Efeito de Relevo)");

        buttonSobelHx.addActionListener(event -> ImageProcessing.sobelFilter(getFileSelector(), true));

        buttonSobelHy.setBounds(170, 800, 140, 40);
        buttonSobelHy.setBackground(new Color(107, 107, 107));
        buttonSobelHy.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonSobelHy.setFont(new Font(null, Font.BOLD, 12));
        buttonSobelHy.setForeground(new Color(229,229,229));
        buttonSobelHy.setFocusable(false);
        buttonSobelHy.setToolTipText("Sobel Hy (Mais Sensível ao Gradiente dos Tons de Cinza na Direção Vertical – Efeito de Relevo)");

        buttonSobelHy.addActionListener(event -> ImageProcessing.sobelFilter(getFileSelector(), false));

        //---------------------------------------------------------

        matrixFilter_1_1.setBounds(20, 850, 90, 40);
        matrixFilter_1_1.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_1_1.setBackground(new Color(68,68,68));
        matrixFilter_1_1.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_1_1.setForeground(new Color(204, 204, 204));
        matrixFilter_1_1.setCaretColor(new Color(204, 204, 204));

        matrixFilter_1_2.setBounds(120, 850, 90, 40);
        matrixFilter_1_2.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_1_2.setBackground(new Color(68,68,68));
        matrixFilter_1_2.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_1_2.setForeground(new Color(204, 204, 204));
        matrixFilter_1_2.setCaretColor(new Color(204, 204, 204));

        matrixFilter_1_3.setBounds(220, 850, 90, 40);
        matrixFilter_1_3.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_1_3.setBackground(new Color(68,68,68));
        matrixFilter_1_3.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_1_3.setForeground(new Color(204, 204, 204));
        matrixFilter_1_3.setCaretColor(new Color(204, 204, 204));

        //---------------------------------------------------------

        matrixFilter_2_1.setBounds(20, 900, 90, 40);
        matrixFilter_2_1.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_2_1.setBackground(new Color(68,68,68));
        matrixFilter_2_1.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_2_1.setForeground(new Color(204, 204, 204));
        matrixFilter_2_1.setCaretColor(new Color(204, 204, 204));

        matrixFilter_2_2.setBounds(120, 900, 90, 40);
        matrixFilter_2_2.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_2_2.setBackground(new Color(68,68,68));
        matrixFilter_2_2.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_2_2.setForeground(new Color(204, 204, 204));
        matrixFilter_2_2.setCaretColor(new Color(204, 204, 204));

        matrixFilter_2_3.setBounds(220, 900, 90, 40);
        matrixFilter_2_3.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_2_3.setBackground(new Color(68,68,68));
        matrixFilter_2_3.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_2_3.setForeground(new Color(204, 204, 204));
        matrixFilter_2_3.setCaretColor(new Color(204, 204, 204));

        //---------------------------------------------------------

        matrixFilter_3_1.setBounds(20, 950, 90, 40);
        matrixFilter_3_1.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_3_1.setBackground(new Color(68,68,68));
        matrixFilter_3_1.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_3_1.setForeground(new Color(204, 204, 204));
        matrixFilter_3_1.setCaretColor(new Color(204, 204, 204));

        matrixFilter_3_2.setBounds(120, 950, 90, 40);
        matrixFilter_3_2.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_3_2.setBackground(new Color(68,68,68));
        matrixFilter_3_2.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_3_2.setForeground(new Color(204, 204, 204));
        matrixFilter_3_2.setCaretColor(new Color(204, 204, 204));

        matrixFilter_3_3.setBounds(220, 950, 90, 40);
        matrixFilter_3_3.setFont(new Font(null, Font.BOLD, 12));
        matrixFilter_3_3.setBackground(new Color(68,68,68));
        matrixFilter_3_3.setBorder(new LineBorder(new Color(68,68,68),10));
        matrixFilter_3_3.setForeground(new Color(204, 204, 204));
        matrixFilter_3_3.setCaretColor(new Color(204, 204, 204));

        //---------------------------------------------------------

        buttonApplyFilter.setBounds(20, 1000, 290, 40);
        buttonApplyFilter.setBackground(new Color(107, 107, 107));
        buttonApplyFilter.setBorder(new LineBorder(new Color(107, 107, 107),0));
        buttonApplyFilter.setFont(new Font(null, Font.BOLD, 12));
        buttonApplyFilter.setForeground(new Color(229,229,229));
        buttonApplyFilter.setFocusable(false);
        buttonApplyFilter.setToolTipText("Aplicar Filtro Personalizado");

        buttonApplyFilter.addActionListener(event -> collectMatrixFilterValues());

        //---------------------------------------------------------

        labelHistogramMatching.setBounds(20, 1040, 290, 50);
        labelHistogramMatching.setFont(new Font(null, Font.BOLD, 12));
        labelHistogramMatching.setForeground(new Color(229,229,229));

        histogramMatchingInput.setBounds(20, 1080, 500, 40);
        histogramMatchingInput.setBackground(new Color(76,76,76));

    }

    private void addComponents(){
        
        add(labelFileSelector);                 // Label do seletor
        add(fileSelector);                      // Seletor de Arquivos

        add(labelFileSave);                     // Label do salvamento
        add(fileSave);                          // Campo de escolha de arquivo para salvamento

        add(buttonClear);                       // Botão de Descartar Alterações
        add(buttonFlipHorizontal);              // Botão de Espelhamento Horizontal
        add(buttonFlipVertical);                // Botão de Espelhamento Vertical
        add(buttonCounterclockwiseRotation);    // Botão de Rotacionar 90º Graus no Sentido Anti-Horário
        add(buttonClockwiseRotation);           // Botão de Rotacionar 90º Graus no Sentido Horário
        add(buttonHistogram);                   // Botão de Criação de Histograma para imagem em tons de cinza
        add(buttonHistogramEqualization);       // Botão de Equalização de Histograma para imagem em tons de cinza
        add(buttonGrayscale);                   // Botão de Conversão para escala de cinza
        add(buttonNegative);                    // Botão de Exibir Negativo da Imagem
        add(buttonZoomIn);                      // Botão de Ampliar Imagem 2x

        add(labelQuantization);                 // Label da Quantização
        add(quantizationInput);                 // Input do Valor para a Quantização
        add(buttonQuantization);                // Botão de Quantização de Tons

        add(labelBrightness);                   // Label do Brilho
        add(brightnessInput);                   // Input do Valor para o Brilho
        add(buttonBrightnessInc);               // Botão de Aumentar Brilho
        add(buttonBrightnessDec);               // Botão de Diminuir Brilho

        add(labelContrast);                     // Label do Contraste
        add(contrastInput);                     // Input do Valor para o Contraste
        add(buttonContrast);                    // Botão de Aplicar Contraste

        add(labelZoomOut);                      // Label do Zoom Out
        add(zoomOutSxInput);                    // Input do Valor Sx para o Cálculo do Zoom Out
        add(zoomOutSyInput);                    // Input do Valor Sy para o Cálculo do Zoom Out
        add(buttonZoomOut);                     // Botão de Aplicar o Zoom Out

        add(labelFilter);                       // Label dos Filtros
        add(buttonGaussian);                    // Botão de Aplicar Filtro Gaussiano
        add(buttonLaplacian);                   // Botão de Aplicar Filtro Laplaciano
        add(buttonGenericHighPass);             // Botão de Aplicar Filtro Passa Altas Genérico
        add(buttonPrewittHx);                   // Botão de Aplicar Filtro Prewitt Hx
        add(buttonPrewittHy);                   // Botão de Aplicar Filtro Prewitt Hy
        add(buttonSobelHx);                     // Botão de Aplicar Filtro Sobel Hx
        add(buttonSobelHy);                     // Botão de Aplicar Filtro Sobel Hy
        add(matrixFilter_1_1);                  // Input 1,1 da Matriz de Filtro Personalizado
        add(matrixFilter_1_2);                  // Input 1,2 da Matriz de Filtro Personalizado
        add(matrixFilter_1_3);                  // Input 1,3 da Matriz de Filtro Personalizado
        add(matrixFilter_2_1);                  // Input 2,1 da Matriz de Filtro Personalizado
        add(matrixFilter_2_2);                  // Input 2,2 da Matriz de Filtro Personalizado
        add(matrixFilter_2_3);                  // Input 2,3 da Matriz de Filtro Personalizado
        add(matrixFilter_3_1);                  // Input 3,1 da Matriz de Filtro Personalizado
        add(matrixFilter_3_2);                  // Input 3,2 da Matriz de Filtro Personalizado
        add(matrixFilter_3_3);                  // Input 3,3 da Matriz de Filtro Personalizado
        add(buttonApplyFilter);                 // Botão de Aplicar Filtro Personalizado

        add(labelHistogramMatching);            // Label do Seletor de Imagem para o Histogram Matching
        add(histogramMatchingInput);            // Seletor de Arquivos para o Histogram Matching

    }

    private void collectMatrixFilterValues(){
        if(getFileSelector().getModifiedImageScreen() != null)
            {
            try{
                double field1_1 = Double.parseDouble(matrixFilter_1_1.getText());
                double field1_2 = Double.parseDouble(matrixFilter_1_2.getText());
                double field1_3 = Double.parseDouble(matrixFilter_1_3.getText());
                double field2_1 = Double.parseDouble(matrixFilter_2_1.getText());
                double field2_2 = Double.parseDouble(matrixFilter_2_2.getText());
                double field2_3 = Double.parseDouble(matrixFilter_2_3.getText());
                double field3_1 = Double.parseDouble(matrixFilter_3_1.getText());
                double field3_2 = Double.parseDouble(matrixFilter_3_2.getText());
                double field3_3 = Double.parseDouble(matrixFilter_3_3.getText());

                double matrixFilter[][] = {
                    {field1_1, field1_2, field1_3},
                    {field2_1, field2_2, field2_3},
                    {field3_1, field3_2, field3_3}
                };
    
                ImageProcessing.personalizedFilter(getFileSelector(), matrixFilter);
    
                fileSelector.getModifiedImageScreen().reloadImg();
                
            }catch(Exception e){
                UserAlert userAlert = new UserAlert("Campo(s) da matriz com valores inválidos.");
            }

        }else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public JLabel getLabelFileSelector() {
        return labelFileSelector;
    }
    public void setLabelFileSelector(JLabel labelFileSelector) {
        this.labelFileSelector = labelFileSelector;
    }


    public FileSelector getFileSelector() {
        return fileSelector;
    }
    public void setFileSelector(FileSelector fileSelector) {
        this.fileSelector = fileSelector;
    }

    public JLabel getLabelFileSave() {
        return labelFileSave;
    }
    public void setLabelFileSave(JLabel labelFileSave) {
        this.labelFileSave = labelFileSave;
    }

    public FileSaver getFileSave() {
        return fileSave;
    }
    public void setFileSave(FileSaver fileSave) {
        this.fileSave = fileSave;
    }

    public JButton getButtonClear() {
        return buttonClear;
    }
    public void setButtonClear(JButton buttonClear) {
        this.buttonClear = buttonClear;
    }
    
    public JButton getButtonFlipHorizontal() {
        return buttonFlipHorizontal;
    }
    public void setButtonFlipHorizontal(JButton buttonFlipHorizontal) {
        this.buttonFlipHorizontal = buttonFlipHorizontal;
    }

    public JButton getButtonFlipVertical() {
        return buttonFlipVertical;
    }
    public void setButtonFlipVertical(JButton buttonFlipVertical) {
        this.buttonFlipVertical = buttonFlipVertical;
    }

    public JButton getButtonGrayscale() {
        return buttonGrayscale;
    }
    public void setButtonGrayscale(JButton buttonGrayscale) {
        this.buttonGrayscale = buttonGrayscale;
    }

    public JLabel getLabelQuantization() {
        return labelQuantization;
    }
    public void setLabelQuantization(JLabel labelQuantization) {
        this.labelQuantization = labelQuantization;
    }

    public JFormattedTextField getQuantizationInput() {
        return quantizationInput;
    }
    public void setQuantizationInput(JFormattedTextField quantizationInput) {
        this.quantizationInput = quantizationInput;
    }

    public JButton getButtonQuantization() {
        return buttonQuantization;
    }
    public void setButtonQuantization(JButton buttonQuantization) {
        this.buttonQuantization = buttonQuantization;
    }

    public JButton getButtonHistogram() {
        return buttonHistogram;
    }
    public void setButtonHistogram(JButton buttonHistogram) {
        this.buttonHistogram = buttonHistogram;
    }

    public JButton getButtonHistogramEqualization() {
        return buttonHistogramEqualization;
    }
    public void setButtonHistogramEqualization(JButton buttonHistogramEqualization) {
        this.buttonHistogramEqualization = buttonHistogramEqualization;
    }

    public JLabel getLabelBrightness() {
        return labelBrightness;
    }
    public void setLabelBrightness(JLabel labelBrightness) {
        this.labelBrightness = labelBrightness;
    }
    
    public JFormattedTextField getBrightnessInput() {
        return brightnessInput;
    }
    public void setBrightnessInput(JFormattedTextField brightnessInput) {
        this.brightnessInput = brightnessInput;
    }

    public JButton getButtonBrightnessInc() {
        return buttonBrightnessInc;
    }
    public void setButtonBrightnessInc(JButton buttonBrightnessInc) {
        this.buttonBrightnessInc = buttonBrightnessInc;
    }

    public JButton getButtonBrightnessDec() {
        return buttonBrightnessDec;
    }
    public void setButtonBrightnessDec(JButton buttonBrightnessDec) {
        this.buttonBrightnessDec = buttonBrightnessDec;
    }

    public JLabel getLabelContrast() {
        return labelContrast;
    }
    public void setLabelContrast(JLabel labelContrast) {
        this.labelContrast = labelContrast;
    }

    public JFormattedTextField getContrastInput() {
        return contrastInput;
    }
    public void setContrastInput(JFormattedTextField contrastInput) {
        this.contrastInput = contrastInput;
    }

    public JButton getButtonContrast() {
        return buttonContrast;
    }
    public void setButtonContrast(JButton buttonContrast) {
        this.buttonContrast = buttonContrast;
    }

    public JButton getButtonNegative() {
        return buttonNegative;
    }
    public void setButtonNegative(JButton buttonNegative) {
        this.buttonNegative = buttonNegative;
    }

    public JButton getButtonZoomIn() {
        return buttonZoomIn;
    }
    public void setButtonZoomIn(JButton buttonZoomIn) {
        this.buttonZoomIn = buttonZoomIn;
    }

    public JLabel getLabelZoomOut() {
        return labelZoomOut;
    }
    public void setLabelZoomOut(JLabel labelZoomOut) {
        this.labelZoomOut = labelZoomOut;
    }

    public JFormattedTextField getZoomOutSxInput() {
        return zoomOutSxInput;
    }
    public void setZoomOutSxInput(JFormattedTextField zoomOutSxInput) {
        this.zoomOutSxInput = zoomOutSxInput;
    }

    public JFormattedTextField getZoomOutSyInput() {
        return zoomOutSyInput;
    }
    public void setZoomOutSyInput(JFormattedTextField zoomOutSyInput) {
        this.zoomOutSyInput = zoomOutSyInput;
    }

    public JButton getButtonZoomOut() {
        return buttonZoomOut;
    }
    public void setButtonZoomOut(JButton buttonZoomOut) {
        this.buttonZoomOut = buttonZoomOut;
    }

    public JLabel getLabelFilter() {
        return labelFilter;
    }
    public void setLabelFilter(JLabel labelFilter) {
        this.labelFilter = labelFilter;
    }

    public JButton getButtonGaussian() {
        return buttonGaussian;
    }
    public void setButtonGaussian(JButton buttonGaussian) {
        this.buttonGaussian = buttonGaussian;
    }

    public JButton getButtonLaplacian() {
        return buttonLaplacian;
    }
    public void setButtonLaplacian(JButton buttonLaplacian) {
        this.buttonLaplacian = buttonLaplacian;
    }

    public JButton getButtonGenericHighPass() {
        return buttonGenericHighPass;
    }
    public void setButtonGenericHighPass(JButton buttonGenericHighPass) {
        this.buttonGenericHighPass = buttonGenericHighPass;
    }

    public JButton getButtonPrewittHx() {
        return buttonPrewittHx;
    }
    public void setButtonPrewittHx(JButton buttonPrewittHx) {
        this.buttonPrewittHx = buttonPrewittHx;
    }

    public JButton getButtonPrewittHy() {
        return buttonPrewittHy;
    }
    public void setButtonPrewittHy(JButton buttonPrewittHy) {
        this.buttonPrewittHy = buttonPrewittHy;
    }

    public JButton getButtonSobelHx() {
        return buttonSobelHx;
    }
    public void setButtonSobelHx(JButton buttonSobelHx) {
        this.buttonSobelHx = buttonSobelHx;
    }

    public JButton getButtonSobelHy() {
        return buttonSobelHy;
    }
    public void setButtonSobelHy(JButton buttonSobelHy) {
        this.buttonSobelHy = buttonSobelHy;
    }

    public JFormattedTextField getMatrixFilter_1_1() {
        return matrixFilter_1_1;
    }
    public void setMatrixFilter_1_1(JFormattedTextField matrixFilter_1_1) {
        this.matrixFilter_1_1 = matrixFilter_1_1;
    }

    public JFormattedTextField getMatrixFilter_1_2() {
        return matrixFilter_1_2;
    }
    public void setMatrixFilter_1_2(JFormattedTextField matrixFilter_1_2) {
        this.matrixFilter_1_2 = matrixFilter_1_2;
    }

    public JFormattedTextField getMatrixFilter_1_3() {
        return matrixFilter_1_3;
    }
    public void setMatrixFilter_1_3(JFormattedTextField matrixFilter_1_3) {
        this.matrixFilter_1_3 = matrixFilter_1_3;
    }

    public JFormattedTextField getMatrixFilter_2_1() {
        return matrixFilter_2_1;
    }
    public void setMatrixFilter_2_1(JFormattedTextField matrixFilter_2_1) {
        this.matrixFilter_2_1 = matrixFilter_2_1;
    }

    public JFormattedTextField getMatrixFilter_2_2() {
        return matrixFilter_2_2;
    }
    public void setMatrixFilter_2_2(JFormattedTextField matrixFilter_2_2) {
        this.matrixFilter_2_2 = matrixFilter_2_2;
    }

    public JFormattedTextField getMatrixFilter_2_3() {
        return matrixFilter_2_3;
    }
    public void setMatrixFilter_2_3(JFormattedTextField matrixFilter_2_3) {
        this.matrixFilter_2_3 = matrixFilter_2_3;
    }

    public JFormattedTextField getMatrixFilter_3_1() {
        return matrixFilter_3_1;
    }
    public void setMatrixFilter_3_1(JFormattedTextField matrixFilter_3_1) {
        this.matrixFilter_3_1 = matrixFilter_3_1;
    }

    public JFormattedTextField getMatrixFilter_3_2() {
        return matrixFilter_3_2;
    }
    public void setMatrixFilter_3_2(JFormattedTextField matrixFilter_3_2) {
        this.matrixFilter_3_2 = matrixFilter_3_2;
    }

    public JFormattedTextField getMatrixFilter_3_3() {
        return matrixFilter_3_3;
    }
    public void setMatrixFilter_3_3(JFormattedTextField matrixFilter_3_3) {
        this.matrixFilter_3_3 = matrixFilter_3_3;
    }

    public void setButtonApplyFilter(JButton buttonApplyFilter) {
        this.buttonApplyFilter = buttonApplyFilter;
    }
    public JButton getButtonApplyFilter() {
        return buttonApplyFilter;
    }

    public JButton getButtonClockwiseRotation() {
        return buttonClockwiseRotation;
    }
    public void setButtonClockwiseRotation(JButton buttonClockwiseRotation) {
        this.buttonClockwiseRotation = buttonClockwiseRotation;
    }

    public JButton getButtonCounterclockwiseRotation() {
        return buttonCounterclockwiseRotation;
    }
    public void setButtonCounterclockwiseRotation(JButton buttonCounterclockwiseRotation) {
        this.buttonCounterclockwiseRotation = buttonCounterclockwiseRotation;
    }

    public void setLabelHistogramMatching(JLabel labelHistogramMatching) {
        this.labelHistogramMatching = labelHistogramMatching;
    }
    public JLabel getLabelHistogramMatching() {
        return labelHistogramMatching;
    }

    public HistogramMatchingFileSelector getHistogramMatchingInput() {
        return histogramMatchingInput;
    }
    public void setHistogramMatchingInput(HistogramMatchingFileSelector histogramMatchingInput) {
        this.histogramMatchingInput = histogramMatchingInput;
    }

}
