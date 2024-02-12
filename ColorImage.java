import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ColorImage {
    private int width;
    private int height;
    private int depth;
    private ArrayList<int[]> pixels;

    public ColorImage(String filename) {
        try {
            readImageFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads image from file assuming PPM format
    private void readImageFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            // Reads the header and comment
            String header = br.readLine();
            String comment = br.readLine();

            // Reads the width, height and depth
            String[] dimensions = br.readLine().split("\\s");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
            depth = Integer.parseInt(br.readLine());

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

    // Method to get the 3-channel value of a pixel at column i, row j
    public int[] getPixel(int i, int j) {
        int index = j * 5 + i;
        return pixels.get(index);
    }

    public void reduceColor(int d) {
        for (int i = 0; i < pixels.size(); i++) {
            int[] pixel = pixels.get(i);

            for (int c = 0; c < pixel.length; c++) {
                pixel[c] = pixel[c] >> (8 - d);
            }
        }
    }
}
