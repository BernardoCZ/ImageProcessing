package Content;

import javax.swing.*;

import Alert.UserAlert;
import ImageProcessing.ImageProcessing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Histogram extends JPanel {
    private int[] histogram;
    private int maxCount;
    private BufferedImage img;

    public Histogram(BufferedImage img)
    {
        setImg(img);
        calcHistogram(img);
        setPreferredSize(new Dimension(850, 300));
        setVisible(true);
    }

    private void calcHistogram(BufferedImage img)
    {
        histogram = new int[256];
        maxCount = 0;

        // Pega os bytes dos pixels da bufferedImage
        final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();

        final int pixelLength = 3;
        for (int pixel = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
            int r, g, b;
            
            r = ((int) pixels[pixel + 2] & 0xff);
            g = ((int) pixels[pixel + 1] & 0xff);
            b = ((int) pixels[pixel] & 0xff);

            int average = (int) ( (r * 0.299 + g * 0.587 + b * 0.114) + 0.0001); //Por conta de problemas de conversão para int adicionamos +0.0001 para garantir que, por exemplo, 113.999999 converta para 114 e não 113

            histogram[average]++;
        }

        // int[][] imgMatrix = ImageProcessing.getImgMatrixRGBA(img);

        // for(int l = 0; l < img.getHeight(); l++)
        // {
        //     for(int c = 0; c < img.getWidth(); c++)
        //     {
        //         int color = (imgMatrix[l][c] >> 16);
        //         histogram[color]++;
        //     }
        // }

        for (int i = 0; i < 256; i++){
            if(histogram[i] > maxCount)
            {
                maxCount = histogram[i];
            }
        }
    }

    public int[] getNormalizedCumulativeHistogram()
    {
        int[] hist = getHistogram();

        double[] hist_cum = new double[256];
        int[] hist_aux = new int[256];
        double alpha = 255.0 / (getImg().getHeight() * getImg().getWidth());

        hist_cum[0] = (int) (alpha * hist[0]);
        for(int i = 1; i < 256; i++)
        {
            hist_cum[i] = (hist_cum[i-1] + (alpha * hist[i]));
            hist_aux[i] = (int) hist_cum[i];
        }

        return hist_aux;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        int barWidth = width / histogram.length;
        int xAxisPadding = 20;
        int yAxisPadding = 40;
        g.setColor(new Color(107, 107, 107));
        
        g.drawLine(yAxisPadding, height - xAxisPadding, yAxisPadding, 10);
        g.drawLine(yAxisPadding, height - xAxisPadding, width - 10, height - xAxisPadding);

        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) ((double) histogram[i] / maxCount * (height - xAxisPadding - 10));
            g.setColor(new Color(229,229,229));
            g.fillRect(yAxisPadding + i * barWidth, height - xAxisPadding - barHeight, barWidth - 2, barHeight);
        }

        g.setColor(new Color(229,229,229));
        g.setFont(new Font(null, Font.BOLD, 12));
        g.drawString("0", yAxisPadding - 20, height - xAxisPadding + 5);
        g.drawString(Integer.toString(maxCount), yAxisPadding - 30, height - xAxisPadding - (height - xAxisPadding - 10) + 5);
        g.drawString(Integer.toString((int) (maxCount / 2)), yAxisPadding - 30, height - xAxisPadding - (height - xAxisPadding - 10) / 2 + 5);

        int labelSkip = histogram.length > 20 ? histogram.length / 10 : 1;
        for (int i = 0; i < histogram.length; i += labelSkip) {
            g.drawString(Integer.toString(i), yAxisPadding + i * barWidth, height - xAxisPadding + 15);
        }
    }

    public int[] getHistogram() {
        return histogram;
    }
    public void setHistogram(int[] histogram) {
        this.histogram = histogram;
    }

    public BufferedImage getImg() {
        return img;
    }
    public void setImg(BufferedImage img) {
        this.img = img;
    }

}