import java.io.*;
import java.util.Arrays;

public class ColorHistogram {

    private int bitDepth;
    private double[] histogram;
    private ColorImage image;

    // Constructor for a d-bit image
    public ColorHistogram(int d) {
        this.bitDepth = d;
        this.histogram = new double[(int) Math.pow(2, d)];
    }

    // Constructor to construct a ColorHistogram from a text file
    public ColorHistogram(String filename) {
        loadHistogramFromFile(filename);
    }

    // Associate an image with the histogram instance
    public void setImage(ColorImage image) {
        this.image = image;
        computeHistogram();
    }

    // Compute and return the normalized histogram of the image
    public double[] getHistogram() {
        return Arrays.copyOf(histogram, histogram.length);
    }

    // Compare two histograms and return the intersection
    public double compare(ColorHistogram hist) {
        double[] otherHistogram = hist.getHistogram();
        double intersection = 0.0;

        for (int i = 0; i < histogram.length; i++) {
            intersection += Math.min(histogram[i], otherHistogram[i]);
        }

        return intersection;
    }

    // Save the histogram into a text file
    public void save(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (double value : histogram) {
                writer.println(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the histogram from a text file
    private void loadHistogramFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < histogram.length) {
                histogram[index++] = Double.parseDouble(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Compute the histogram from the associated image
    private void computeHistogram() {
        // Assuming image.getColor() returns color values in the range [0, 2^bitDepth - 1]
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int[]  colorValue = image.getPixel(i, j);
                histogram[colorValue]++;
            }
        }

        // Normalize the histogram
        normalizeHistogram();
    }

    // Normalize the histogram by dividing each bin count by the total number of pixels
    private void normalizeHistogram() {
        int totalPixels = image.getWidth() * image.getHeight();
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] /= totalPixels;
        }
    }

    public static void main(String[] args) {
        ColorHistogram histogram = new ColorHistogram("imageDataset2_15_20/25.jpg.txt");
    }
}
