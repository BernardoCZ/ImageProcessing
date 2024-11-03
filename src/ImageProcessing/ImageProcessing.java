package ImageProcessing;

import Input.FileSelector;
import Screen.HistogramScreen;
import Screen.ImageScreen;
import Screen.ModifiedImageScreen;
import Screen.Screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import Alert.UserAlert;
import Content.Histogram;

public class ImageProcessing {

    // Função que faz cópia do buffer de uma imagem, retornando o novo buffer
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static void clear(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            fileSelector.getModifiedImageScreen().setHorizontallyFlipped(false);
            fileSelector.getModifiedImageScreen().setVerticallyFlipped(false);
            fileSelector.getModifiedImageScreen().setImg(deepCopy(fileSelector.getBaseImageScreen().getImg()));
            fileSelector.getModifiedImageScreen().reloadImg();
        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    // Recebe o buffer de uma imagem e retorna um matriz de cores relacionada
    public static int[][] getImgMatrixRGBA(BufferedImage img) {

        final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
  
        int height = img.getHeight();
        int width = img.getWidth();

        int[][] result = new int[height][width];

        int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
            int argb = 0;

            // Posiciona os conjuntos de 8 bits da imagem em um padrão de 32 bits com -> red | green | blue
            argb += ((int) pixels[pixel] & 0xff);                // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8);     // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16);    // red

            result[row][col] = argb;

            col++;

            if (col == width) {
                col = 0;
                row++;
            }
        }
  
        return result;
    }

    public static void flipHorizontal(FileSelector fileSelector)
    {
        BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        int[][] imgMatrix = getImgMatrixRGBA(img);

        for(int i = 0; i < img.getHeight(); i++)
        {
            for(int j = 0; j < img.getWidth(); j++)
            {
                newImg.setRGB(img.getWidth() -1 - j, i, imgMatrix[i][j]);
            }
        }

        fileSelector.getModifiedImageScreen().setImg(newImg);
        fileSelector.getModifiedImageScreen().setHorizontallyFlipped(
            !(fileSelector.getModifiedImageScreen().isHorizontallyFlipped()));

    }

    public static void eventFlipHorizontal(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            flipHorizontal(fileSelector);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void flipVertical(FileSelector fileSelector)
    {
        BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        int[][] imgMatrix = getImgMatrixRGBA(img);

        for(int i = 0; i < img.getHeight(); i++)
        {
            for(int j = 0; j < img.getWidth(); j++)
            {
                newImg.setRGB(j, img.getHeight() -1 - i, imgMatrix[i][j]);
            }
        }

        fileSelector.getModifiedImageScreen().setImg(newImg);
        fileSelector.getModifiedImageScreen().setVerticallyFlipped(
            !(fileSelector.getModifiedImageScreen().isVerticallyFlipped()));
    }

    public static void eventFlipVertical(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            flipVertical(fileSelector);
            fileSelector.getModifiedImageScreen().reloadImg();
        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void rotation90(FileSelector fileSelector, boolean clockwise)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            int height = img.getHeight();
            int width = img.getWidth();

            BufferedImage newImg = new BufferedImage(height, width, img.getType());

            for(int l = 0; l < height; l++){
                for(int c = 0; c < width; c++){
                    if(clockwise)
                    {
                        newImg.setRGB(height - l - 1, c, imgMatrix[l][c]);
                    }
                    else{
                        newImg.setRGB(l, width - c - 1, imgMatrix[l][c]);
                    }
                }
            }

            fileSelector.getModifiedImageScreen().setImg(newImg);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void zoomIn2x2(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            int height = (img.getHeight() * 2) -1;
            int width = (img.getWidth() * 2) -1;

            BufferedImage newImg = new BufferedImage(width, height, img.getType());

            // Insere uma linha e uma coluna em branco entre cada duas linhas e colunas originais, respectivamente
            for(int l = 0; l < height; l += 2){
                for(int c = 0; c < width; c += 2){
                    newImg.setRGB(c, l, imgMatrix[(int) (l/2)][(int) (c/2)]);
                }
            }

            // Interpola lineramente os valores para preencher todas as linhas que já possuem alguns valores
            for(int l = 0; l < height; l += 2)
            {
                for(int c = 1; c < width; c += 2)
                {
                    int argb = 0;

                    // Aplicando interpolação
                    int r = (int) ( (
                        (imgMatrix[(int) (l/2)][(int) ((c-1)/2)] >> 16)
                        +
                        (imgMatrix[(int) (l/2)][(int) ((c+1)/2)] >> 16)
                    ) / 2 );

                    int g = (int) ( (
                        ((imgMatrix[(int) (l/2)][(int) ((c-1)/2)] << 16) >>> 24)
                        +
                        ((imgMatrix[(int) (l/2)][(int) ((c+1)/2)] << 16) >>> 24)
                    ) / 2 );

                    int b = (int) ( (
                        ((imgMatrix[(int) (l/2)][(int) ((c-1)/2)] << 24) >>> 24)
                        +
                        ((imgMatrix[(int) (l/2)][(int) ((c+1)/2)] << 24) >>> 24)
                    ) / 2 );


                    argb += (r << 16); // red
                    argb += (g << 8); // green
                    argb += b; // blue

                    newImg.setRGB(c, l, argb);
                }
            }

            imgMatrix = getImgMatrixRGBA(newImg);

            // Interpola lineramente os valores para preencher todas as linhas restantes
            for(int l = 1; l < height; l += 2)
            {
                for(int c = 0; c < width; c++)
                {
                    int argb = 0;

                    // Aplicando interpolação
                    int r = (int) ( (
                        (imgMatrix[l-1][c] >> 16)
                        +
                        (imgMatrix[l+1][c] >> 16)
                    ) / 2 );

                    int g = (int) ( (
                        ((imgMatrix[l-1][c] << 16) >>> 24)
                        +
                        ((imgMatrix[l+1][c] << 16) >>> 24)
                    ) / 2 );

                    int b = (int) ( (
                        ((imgMatrix[l-1][c] << 24) >>> 24)
                        +
                        ((imgMatrix[l+1][c] << 24) >>> 24)
                    ) / 2 );


                    argb += (r << 16); // red
                    argb += (g << 8); // green
                    argb += b; // blue

                    newImg.setRGB(c, l, argb);
                }
            }

            fileSelector.getModifiedImageScreen().setImg(newImg);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void zoomOut(FileSelector fileSelector, String txtSx, String txtSy)
    {
        if(txtSx.length() != 0 && txtSy.length() != 0)
        {
            if(fileSelector.getModifiedImageScreen() != null)
            {
                BufferedImage img = fileSelector.getModifiedImageScreen().getImg();
                int sx = Integer.valueOf(txtSx);
                int sy = Integer.valueOf(txtSy);

                int[][] imgMatrix = getImgMatrixRGBA(img);

                int height = img.getHeight();
                int width = img.getWidth();

                int newHeight = ((height - (height % sy)) / sy) + (height % sy == 0 ? 0 : 1);
                int newWidth = ((width - (width % sx)) / sx) + (width % sx == 0 ? 0 : 1);

                BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());

                for(int nl = 0; nl < newHeight; nl++)
                {
                    for(int nc = 0; nc < newWidth; nc++)
                    {
                        int sumR = 0, sumG = 0, sumB = 0, count = 0, argb = 0;

                        for(int l = nl * sy; (l < (nl+1) * sy) && (l < height); l++)
                        {
                            for(int c = nc * sx; (c < (nc+1) * sx) && (c < width); c++)
                            {
                                sumR += (imgMatrix[l][c] >> 16);
                                sumG += ((imgMatrix[l][c] << 16) >>> 24);
                                sumB += ((imgMatrix[l][c] << 24) >>> 24);
                                count++;
                            }
                        }

                        int r = (int) (sumR / count);
                        int g = (int) (sumG / count);
                        int b = (int) (sumB / count);

                        argb += (r << 16); // red
                        argb += (g << 8); // green
                        argb += b; // blue

                        newImg.setRGB(nc, nl, argb);
                    }
                }
        
                fileSelector.getModifiedImageScreen().setImg(newImg);
                fileSelector.getModifiedImageScreen().reloadImg();
            }
            else{
                UserAlert userAlert = new UserAlert("Você não importou uma imagem");
            }

        }
        else{
            UserAlert userAlert = new UserAlert("Você deve preencher os valores de Sx e Sy");
        }
    }

    public static void grayscale(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

            final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
            
            int width = img.getWidth();
            int pixelLength = 3;

            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0, r, g, b;
                
                r = ((int) pixels[pixel + 2] & 0xff);
                g = ((int) pixels[pixel + 1] & 0xff);
                b = ((int) pixels[pixel] & 0xff);

                int l = (int) ( (r * 0.299 + g * 0.587 + b * 0.114) + 0.0001); //Por conta de problemas de conversão para int adicionamos +0.0001 para garantir que, por exemplo, 113.999999 converta para 114 e não 113
                
                argb += l; // blue
                argb += (l << 8); // green
                argb += (l << 16); // red

                newImg.setRGB(col, row, argb);

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }

            fileSelector.getModifiedImageScreen().setImg(newImg);

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void eventGrayscale(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            grayscale(fileSelector);
            fileSelector.getModifiedImageScreen().reloadImg();
        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static BufferedImage getGrayscale(BufferedImage img)
    {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
        
        int width = img.getWidth();
        int pixelLength = 3;

        for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
            int argb = 0, r, g, b;
            
            r = ((int) pixels[pixel + 2] & 0xff);
            g = ((int) pixels[pixel + 1] & 0xff);
            b = ((int) pixels[pixel] & 0xff);

            int l = (int) ( (r * 0.299 + g * 0.587 + b * 0.114) + 0.0001); //Por conta de problemas de conversão para int adicionamos +0.0001 para garantir que, por exemplo, 113.999999 converta para 114 e não 113
            
            argb += l; // blue
            argb += (l << 8); // green
            argb += (l << 16); // red

            newImg.setRGB(col, row, argb);

            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        return newImg;
    }

    public static void quantization(String txt, FileSelector fileSelector)
    {
        if(txt.length() != 0)
        {
            if(fileSelector.getModifiedImageScreen() != null)
            {
                BufferedImage img = fileSelector.getModifiedImageScreen().getImg();
                int quantizationTones = Integer.valueOf(txt);

                final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
        
                int width = img.getWidth();
                int pixelLength = 3;
                int lumMAX = 0;
                int lumMIN = 255;
                int lumREP;
                int tamBIN;

                // Loop por todos os pixels da imagem para conseguir valores de luminância MÁXIMA e MÍNIMA
                for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                    int lum;
                    
                    // Pega a luminancia
                    lum = ((int) pixels[pixel] & 0xff);

                    if(lum > lumMAX){
                        lumMAX = lum;
                    }
                    if(lum < lumMIN){
                        lumMIN = lum;
                    }

                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }

                lumREP = lumMAX - lumMIN + 1;
                tamBIN = lumREP / quantizationTones;

                // Loop de criação da quantização

                if(quantizationTones < lumREP){
                    for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                        int argb = 0, lum;

                        lum = ((int) pixels[pixel] & 0xff);

                        for(int countBIN = 1; countBIN <= quantizationTones; countBIN++){
                            if((lum >= lumMIN - 0.5 + ((countBIN - 1) * tamBIN))
                            && (lum <= lumMIN - 0.5 + ((countBIN) * tamBIN))
                            ){
                                lum = lumMIN + ((countBIN - 1) * tamBIN);
                            }
                        }
                        
                        argb += lum; // blue
                        argb += (lum << 8); // green
                        argb += (lum << 16); // red

                        img.setRGB(col, row, argb);

                        col++;
                        if (col == width) {
                            col = 0;
                            row++;
                        }
                    }
                }
        
                fileSelector.getModifiedImageScreen().setImg(img);
                fileSelector.getModifiedImageScreen().reloadImg();
            }
            else{
                UserAlert userAlert = new UserAlert("Você não importou uma imagem");
            }

        }
    }

    public static void brightness(String txt, boolean inc, FileSelector fileSelector)
    {
        if(txt.length() != 0)
        {
            if(fileSelector.getModifiedImageScreen() != null)
            {
                BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

                int brightness = 0;
                if(inc) {
                    brightness += Integer.valueOf(txt);
                }
                else{
                    brightness -= Integer.valueOf(txt);
                }

                final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
                
                int width = img.getWidth();
                int pixelLength = 3;

                for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                    int argb = 0, r, g, b;
                    
                    r = ((int) pixels[pixel + 2] & 0xff);
                    g = ((int) pixels[pixel + 1] & 0xff);
                    b = ((int) pixels[pixel] & 0xff);

                    r += brightness;
                    if(r > 255)
                    {
                        r = 255;
                    }
                    else if(r < 0)
                    {
                        r = 0;
                    }

                    g += brightness;
                    if(g > 255)
                    {
                        g = 255;
                    }
                    else if(g < 0)
                    {
                        g = 0;
                    }

                    b += brightness;
                    if(b > 255)
                    {
                        b = 255;
                    }
                    else if(b < 0)
                    {
                        b = 0;
                    }
                    
                    argb += b; // blue
                    argb += (g << 8); // green
                    argb += (r << 16); // red

                    img.setRGB(col, row, argb);

                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                }

                fileSelector.getModifiedImageScreen().setImg(img);
                fileSelector.getModifiedImageScreen().reloadImg();
            }
            else{
                UserAlert userAlert = new UserAlert("Você não importou uma imagem");
            }

        }
    }

    public static void contrast(String txt, FileSelector fileSelector)
    {
        if(txt.length() != 0)
        {
            if(fileSelector.getModifiedImageScreen() != null)
            {
                BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

                String[] txtAux = txt.split(",");

                String txtFirst = txtAux[0];
                String txtSecond = txtAux[1];

                float contrast = Float.valueOf(txtFirst+"."+txtSecond);

                if(contrast > 0)
                {
                    final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
                    
                    int width = img.getWidth();
                    int pixelLength = 3;

                    for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                        int argb = 0, r, g, b;
                        
                        r = ((int) pixels[pixel + 2] & 0xff);
                        g = ((int) pixels[pixel + 1] & 0xff);
                        b = ((int) pixels[pixel] & 0xff);

                        r = (int) (r * contrast);
                        if(r > 255)
                        {
                            r = 255;
                        }

                        g = (int) (g * contrast);
                        if(g > 255)
                        {
                            g = 255;
                        }

                        b = (int) (b * contrast);
                        if(b > 255)
                        {
                            b = 255;
                        }
                        
                        argb += b; // blue
                        argb += (g << 8); // green
                        argb += (r << 16); // red

                        img.setRGB(col, row, argb);

                        col++;
                        if (col == width) {
                            col = 0;
                            row++;
                        }
                    }

                    fileSelector.getModifiedImageScreen().setImg(img);
                    fileSelector.getModifiedImageScreen().reloadImg();
                }
                else {
                    UserAlert userAlert = new UserAlert("O valor do contraste deve ser maior que zero");
                }
            }
            else{
                UserAlert userAlert = new UserAlert("Você não importou uma imagem");
            }

        }
    }

    public static void negative(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); // Pega os bytes dos pixels da bufferedImage
            
            int width = img.getWidth();
            int pixelLength = 3;

            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0, r, g, b;
                
                r = ((int) pixels[pixel + 2] & 0xff);
                g = ((int) pixels[pixel + 1] & 0xff);
                b = ((int) pixels[pixel] & 0xff);

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                
                argb += b; // blue
                argb += (g << 8); // green
                argb += (r << 16); // red

                img.setRGB(col, row, argb);

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }

            fileSelector.getModifiedImageScreen().setImg(img);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void personalizedFilter(FileSelector fileSelector, double matrixFilter[][])
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            grayscale(fileSelector);
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            // Matriz do Filtro Gaussiano
            // a | b | c
            // d | e | f
            // g | h | i

            double a, b, c, d, e, f, g, h, i;

            a = matrixFilter[0][0]; b = matrixFilter[0][1]; c = matrixFilter[0][2];
            d = matrixFilter[1][0]; e = matrixFilter[1][1]; f = -matrixFilter[1][2];
            g = matrixFilter[2][0]; h = matrixFilter[2][1]; i = matrixFilter[2][2];

            for(int lin = 1; lin < img.getHeight() - 1; lin++)
            {
                for(int col = 1; col < img.getWidth() - 1; col++)
                {
                    int argb = 0;

                    // Matriz ao redor do pixel E
                    // A | B | C
                    // D | E | F
                    // G | H | I

                    double A, B, C, D, E, F, G, H, I;

                    // Aplicando convolução
                    A = i * (imgMatrix[lin-1][col-1] >> 16);
                    B = h * (imgMatrix[lin-1][col] >> 16);
                    C = g * (imgMatrix[lin-1][col+1] >> 16);
                    D = f * (imgMatrix[lin][col-1] >> 16);
                    E = e * (imgMatrix[lin][col] >> 16);
                    F = d * (imgMatrix[lin][col+1] >> 16);
                    G = c * (imgMatrix[lin+1][col-1] >> 16);
                    H = b * (imgMatrix[lin+1][col] >> 16);
                    I = a * (imgMatrix[lin+1][col+1] >> 16);
                    int sum = (int) (A+B+C+D+E+F+G+H+I);

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += (sum << 16); // red
                    argb += (sum << 8); // green
                    argb += sum; // blue

                    img.setRGB(col, lin, argb);
                }
            }
            fileSelector.getModifiedImageScreen().setImg(img);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void gaussianFilter(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            // Matriz do Filtro Gaussiano
            // a | b | c
            // d | e | f
            // g | h | i

            double a = 0.0625, b = 0.125, c = 0.0625;
            double d = 0.125, e = 0.25, f = 0.125;
            double g = 0.0625, h = 0.125, i = 0.0625;

            for(int lin = 1; lin < img.getHeight() - 1; lin++)
            {
                for(int col = 1; col < img.getWidth() - 1; col++)
                {
                    int argb = 0;

                    // Matriz ao redor do pixel E
                    // A | B | C
                    // D | E | F
                    // G | H | I

                    double A, B, C, D, E, F, G, H, I;

                    // Aplicando convolução para o canal red
                    A = i * (imgMatrix[lin-1][col-1] >> 16);
                    B = h * (imgMatrix[lin-1][col] >> 16);
                    C = g * (imgMatrix[lin-1][col+1] >> 16);
                    D = f * (imgMatrix[lin][col-1] >> 16);
                    E = e * (imgMatrix[lin][col] >> 16);
                    F = d * (imgMatrix[lin][col+1] >> 16);
                    G = c * (imgMatrix[lin+1][col-1] >> 16);
                    H = b * (imgMatrix[lin+1][col] >> 16);
                    I = a * (imgMatrix[lin+1][col+1] >> 16);
                    int sum = (int) (A+B+C+D+E+F+G+H+I);

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += (sum << 16); // red

                    // Aplicando convolução para o canal green
                    A = i * ((imgMatrix[lin-1][col-1] << 16) >>> 24);
                    B = h * ((imgMatrix[lin-1][col] << 16) >>> 24);
                    C = g * ((imgMatrix[lin-1][col+1] << 16) >>> 24);
                    D = f * ((imgMatrix[lin][col-1] << 16) >>> 24);
                    E = e * ((imgMatrix[lin][col] << 16) >>> 24);
                    F = d * ((imgMatrix[lin][col+1] << 16) >>> 24);
                    G = c * ((imgMatrix[lin+1][col-1] << 16) >>> 24);
                    H = b * ((imgMatrix[lin+1][col] << 16) >>> 24);
                    I = a * ((imgMatrix[lin+1][col+1] << 16) >>> 24);
                    sum = (int) (A+B+C+D+E+F+G+H+I);

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += (sum << 8); // green

                    // Aplicando convolução para o canal blue
                    A = i * ((imgMatrix[lin-1][col-1] << 24) >>> 24);
                    B = h * ((imgMatrix[lin-1][col] << 24) >>> 24);
                    C = g * ((imgMatrix[lin-1][col+1] << 24) >>> 24);
                    D = f * ((imgMatrix[lin][col-1] << 24) >>> 24);
                    E = e * ((imgMatrix[lin][col] << 24) >>> 24);
                    F = d * ((imgMatrix[lin][col+1] << 24) >>> 24);
                    G = c * ((imgMatrix[lin+1][col-1] << 24) >>> 24);
                    H = b * ((imgMatrix[lin+1][col] << 24) >>> 24);
                    I = a * ((imgMatrix[lin+1][col+1] << 24) >>> 24);
                    sum = (int) (A+B+C+D+E+F+G+H+I);

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += sum; // blue

                    img.setRGB(col, lin, argb);
                }
            }
            fileSelector.getModifiedImageScreen().setImg(img);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void highPassFilter(FileSelector fileSelector, boolean generic)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            grayscale(fileSelector);
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            // Matriz do Filtro Gaussiano
            // a | b | c
            // d | e | f
            // g | h | i

            int a, b, c, d, e, f, g, h, i;

            if(generic)
            {
                // Filtro Passa Altas Genérico
                a = -1; b = -1; c = -1;
                d = -1; e = 8; f = -1;
                g = -1; h = -1; i = -1;
            }
            else{
                // Filtro Passa Altas Laplaciano
                a = 0; b = -1; c = 0;
                d = -1; e = 4; f = -1;
                g = 0; h = -1; i = 0;
            }

            for(int lin = 1; lin < img.getHeight() - 1; lin++)
            {
                for(int col = 1; col < img.getWidth() - 1; col++)
                {
                    int argb = 0;

                    // Matriz ao redor do pixel E
                    // A | B | C
                    // D | E | F
                    // G | H | I

                    int A, B, C, D, E, F, G, H, I;

                    // Aplicando convolução
                    A = i * (imgMatrix[lin-1][col-1] >> 16);
                    B = h * (imgMatrix[lin-1][col] >> 16);
                    C = g * (imgMatrix[lin-1][col+1] >> 16);
                    D = f * (imgMatrix[lin][col-1] >> 16);
                    E = e * (imgMatrix[lin][col] >> 16);
                    F = d * (imgMatrix[lin][col+1] >> 16);
                    G = c * (imgMatrix[lin+1][col-1] >> 16);
                    H = b * (imgMatrix[lin+1][col] >> 16);
                    I = a * (imgMatrix[lin+1][col+1] >> 16);
                    int sum = A+B+C+D+E+F+G+H+I;

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += (sum << 16); // red
                    argb += (sum << 8); // green
                    argb += sum; // blue

                    img.setRGB(col, lin, argb);
                }
            }
            fileSelector.getModifiedImageScreen().setImg(img);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void prewittFilter(FileSelector fileSelector, boolean horizontal)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            grayscale(fileSelector);
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            // Matriz do Filtro Gaussiano
            // a | b | c
            // d | e | f
            // g | h | i

            int a, b, c, d, e, f, g, h, i;

            if(horizontal)
            {
                // Filtro Prewitt Hx
                a = -1; b = 0; c = 1;
                d = -1; e = 0; f = 1;
                g = -1; h = 0; i = 1;
            }
            else{
                // Filtro Prewitt Hy
                a = -1; b = -1; c = -1;
                d = 0; e = 0; f = 0;
                g = 1; h = 1; i = 1;
            }

            for(int lin = 1; lin < img.getHeight() - 1; lin++)
            {
                for(int col = 1; col < img.getWidth() - 1; col++)
                {
                    int argb = 0;

                    // Matriz ao redor do pixel E
                    // A | B | C
                    // D | E | F
                    // G | H | I

                    int A, B, C, D, E, F, G, H, I;

                    // Aplicando convolução
                    A = i * (imgMatrix[lin-1][col-1] >> 16);
                    B = h * (imgMatrix[lin-1][col] >> 16);
                    C = g * (imgMatrix[lin-1][col+1] >> 16);
                    D = f * (imgMatrix[lin][col-1] >> 16);
                    E = e * (imgMatrix[lin][col] >> 16);
                    F = d * (imgMatrix[lin][col+1] >> 16);
                    G = c * (imgMatrix[lin+1][col-1] >> 16);
                    H = b * (imgMatrix[lin+1][col] >> 16);
                    I = a * (imgMatrix[lin+1][col+1] >> 16);
                    int sum = A+B+C+D+E+F+G+H+I+127;

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += (sum << 16); // red
                    argb += (sum << 8); // green
                    argb += sum; // blue

                    img.setRGB(col, lin, argb);
                }
            }
            fileSelector.getModifiedImageScreen().setImg(img);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void sobelFilter(FileSelector fileSelector, boolean horizontal)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            grayscale(fileSelector);
            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            // Matriz do Filtro Gaussiano
            // a | b | c
            // d | e | f
            // g | h | i

            int a, b, c, d, e, f, g, h, i;

            if(horizontal)
            {
                // Filtro Sobel Hx
                a = -1; b = 0; c = 1;
                d = -2; e = 0; f = 2;
                g = -1; h = 0; i = 1;
            }
            else{
                // Filtro Sobel Hy
                a = -1; b = -2; c = -1;
                d = 0; e = 0; f = 0;
                g = 1; h = 2; i = 1;
            }

            for(int lin = 1; lin < img.getHeight() - 1; lin++)
            {
                for(int col = 1; col < img.getWidth() - 1; col++)
                {
                    int argb = 0;

                    // Matriz ao redor do pixel E
                    // A | B | C
                    // D | E | F
                    // G | H | I

                    int A, B, C, D, E, F, G, H, I;

                    // Aplicando convolução
                    A = i * (imgMatrix[lin-1][col-1] >> 16);
                    B = h * (imgMatrix[lin-1][col] >> 16);
                    C = g * (imgMatrix[lin-1][col+1] >> 16);
                    D = f * (imgMatrix[lin][col-1] >> 16);
                    E = e * (imgMatrix[lin][col] >> 16);
                    F = d * (imgMatrix[lin][col+1] >> 16);
                    G = c * (imgMatrix[lin+1][col-1] >> 16);
                    H = b * (imgMatrix[lin+1][col] >> 16);
                    I = a * (imgMatrix[lin+1][col+1] >> 16);
                    int sum = A+B+C+D+E+F+G+H+I+127;

                    if(sum > 255)
                    {
                        sum = 255;
                    }
                    else if(sum < 0)
                    {
                        sum = 0;
                    }

                    argb += (sum << 16); // red
                    argb += (sum << 8); // green
                    argb += sum; // blue

                    img.setRGB(col, lin, argb);
                }
            }
            fileSelector.getModifiedImageScreen().setImg(img);
            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }
    }

    public static void histogram(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            Screen.closeOldDefinedScreens();

            HistogramScreen histogramScreen = new HistogramScreen(fileSelector.getModifiedImageScreen().getImg(), "Histograma da Imagem Modificada em Tons de Cinza");

            fileSelector.getModifiedImageScreen().reloadImg();

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }

    }

    public static void histogramEqualization(FileSelector fileSelector)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {

            BufferedImage img = fileSelector.getModifiedImageScreen().getImg();

            int[][] imgMatrix = getImgMatrixRGBA(img);

            int height = img.getHeight();
            int width = img.getWidth();

            BufferedImage newImg = new BufferedImage(width, height, img.getType());

            Histogram hist = new Histogram(img);
            int[] hist_cum = hist.getNormalizedCumulativeHistogram();

            for(int l = 0; l < height; l++)
            {
                for(int c = 0; c < width; c++)
                {
                    int argb = 0;
                    int r = (imgMatrix[l][c] >> 16); //red

                    int g = ((imgMatrix[l][c] << 16) >>> 24); //green

                    int b = ((imgMatrix[l][c] << 24) >>> 24); //blue

                    r = hist_cum[r];
                    g = hist_cum[g];
                    b = hist_cum[b];

                    argb += (r << 16); // red
                    argb += (g << 8); // green
                    argb += b; // blue
                    newImg.setRGB(c, l, argb);
                }
            }

            Screen.closeOldDefinedScreens();

            ImageScreen imageBeforeScreen = new ImageScreen(img, "Imagem Antes da Equalização");

            fileSelector.getModifiedImageScreen().setImg(newImg);
            fileSelector.getModifiedImageScreen().reloadImg();

            HistogramScreen histogramBeforeScreen = new HistogramScreen(hist, "Histograma da Imagem em Tons de Cinza");
            HistogramScreen histogramAfterScreen = new HistogramScreen(newImg, "Histograma da Imagem em Tons de Cinza Após Equalização", true);


        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }

    }

    public static void histogramMatching(FileSelector fileSelector, String filePath)
    {
        if(fileSelector.getModifiedImageScreen() != null)
        {
            if(filePath.length() != 0){

                BufferedImage imgTarget = fileSelector.getModifiedImageScreen().getImg();
                try {                
                    imgTarget = ImageIO.read(new File(filePath));
                } catch (IOException ex) {
                    UserAlert userAlert = new UserAlert("ERRO - Erro ao carregar imagem para o matching");
                }

                Screen.closeOldDefinedScreens();

                grayscale(fileSelector);

                BufferedImage imgSource = fileSelector.getModifiedImageScreen().getImg();

                int[][] imgSourceMatrix = getImgMatrixRGBA(imgSource);

                int height = imgSource.getHeight();
                int width = imgSource.getWidth();

                BufferedImage newImg = new BufferedImage(width, height, imgSource.getType());

                Histogram source_hist = new Histogram(imgSource);
                int[] source_hist_cum = source_hist.getNormalizedCumulativeHistogram();

                Histogram target_hist = new Histogram(imgTarget);
                int[] target_hist_cum = target_hist.getNormalizedCumulativeHistogram();

                int[] hist_matching = new int[256];

                for(int i=0; i < 256; i++)
                {
                    int new_shade = 0;
                    int mostSimilar = 255;

                    for(int j=0; j < 256; j++)
                    {
                        if(Math.abs(source_hist_cum[i] - target_hist_cum[j]) < mostSimilar)
                        {
                            mostSimilar = Math.abs(source_hist_cum[i] - target_hist_cum[j]);
                            new_shade = j;
                        }
                    }

                    hist_matching[i] = new_shade;
                }

                for(int l = 0; l < height; l++)
                {
                    for(int c = 0; c < width; c++)
                    {
                        int argb = 0;
                        int lum = (imgSourceMatrix[l][c] >> 16);
                        int v = hist_matching[lum];

                        argb += (v << 16); // red
                        argb += (v << 8); // green
                        argb += v; // blue
                        newImg.setRGB(c, l, argb);
                    }
                }

                Screen.closeOldDefinedScreens();

                ImageScreen imageTargetScreen = new ImageScreen(getGrayscale(imgTarget), "Imagem Alvo para o Matching");

                fileSelector.getModifiedImageScreen().setImg(newImg);
                fileSelector.getModifiedImageScreen().reloadImg();

            }
            else{
                UserAlert userAlert = new UserAlert("Você não selecionou uma imagem para fazer o matching!");
            }

        }
        else{
            UserAlert userAlert = new UserAlert("Você não importou uma imagem");
        }

    }

}
