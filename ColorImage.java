/*
 * Group Members: Charley Liu, Oscar (Tsz Kit) Law
 * Student IDs: 300304744, 300306180
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ColorImage {
    private int width;
    private int height;
    private int depth;
    private ArrayList<int[]> pixels;

    /**
     * A constructor that creates an image from a file
     * 
     * @param filename The image's filename
     */
    public ColorImage(String filename) {
        try {
            readImageFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that reads data from a PPM file
     * 
     * @param filename A ppm file
     * @throws IOException if file is not found
     */
    private void readImageFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            // Reads the header and comment
            String header = br.readLine();
            String comment = br.readLine();

            // Reads the width, height and depth
            String[] dimensions = br.readLine().split("\\s");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
            depth = (int)((double)Math.log(Integer.parseInt(br.readLine())) / Math.log(2));

            // Reads the pixel values and adds them to an ArrayList
            pixels = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s");
                for (int i = 0; i < values.length; i += 3) {
                    int r = Integer.parseInt(values[i]);
                    int g = Integer.parseInt(values[i + 1]);
                    int b = Integer.parseInt(values[i + 2]);
                    pixels.add(new int[]{r, g, b});
                }
            }

        }
    }

    // Getter methods
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getDepth() {
        return depth;
    }

    /**
     * A method to get the 3-channel value of a pixel at column i, row j
     * 
     * @param i The column of the pixel to search
     * @param j The row of the pixel to search
     * @return An int array of size 3 representing the rgb values of the pixel
     */
    public int[] getPixel(int i, int j) {
        int index = j * width + i;
        return pixels.get(index);
    }

    /**
     * A method that reduces the color space of the image to a d-bit representation
     * 
     * @param d the number of bits per pixel
     */
    public void reduceColor(int d) {
        this.depth = d;
        
        for (int i = 0; i < pixels.size(); i++) {
            int[] pixel = pixels.get(i);

            for (int c = 0; c < pixel.length; c++) {
                pixel[c] = pixel[c] >> (8 - d);
            }
        }
    }
}
