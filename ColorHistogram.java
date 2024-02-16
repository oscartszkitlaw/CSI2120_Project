import java.io.*;
import java.util.StringTokenizer;

public class ColorHistogram {
    private int bitDepth;
    private double[] histogram;
    private ColorImage image;

    // Constructor for a d-bit image
    public ColorHistogram(int d) {
        this.bitDepth = d;
        this.histogram = new double[(int) Math.pow(2, d * 3)];
    }

    // Constructor to construct a ColorHistogram from a text file
    public ColorHistogram(String filename) {
        loadHistogramFromFile(filename);
    }

    // Associate an image with the histogram instance
    public void setImage(ColorImage image) {
        this.image = image;
        this.image.reduceColor(3);
        computeHistogram();
    }

    // Compute and return the normalized histogram of the image
    public double[] getHistogram() {
        double[] normalizedHistogram = new double[histogram.length];

        // count the total number of pixels
        int totalPixels = 0;
        for (double count : histogram) {
            totalPixels += (int)count;
        }
        // normalize histogram by dividing
        for (int i = 0; i < histogram.length; i++) {
            normalizedHistogram[i] = histogram[i] / totalPixels;
        }

        return normalizedHistogram;
    }

    // Compare two histograms and return the intersection
    public double compare(ColorHistogram hist) {
        double[] thisHistogram = this.getHistogram();
        double[] otherHistogram = hist.getHistogram();
        double intersection = 0.0;

        for (int i = 0; i < thisHistogram.length; i++) {
            intersection += Math.min(thisHistogram[i], otherHistogram[i]);
        }

        return intersection;
    }

    // Save the histogram into a text file
    public void save(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println((int) Math.pow(2, this.bitDepth * 3));
            for (double value : histogram) {
                writer.print(value + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the histogram from a text file
    private void loadHistogramFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            // get number of bins and depth of image
            int numBins = Integer.parseInt(reader.readLine());
            this.bitDepth = (int)(Math.log(numBins) / (3 * Math.log(2)));
            this.histogram = new double[numBins];

            // load the histogram into the array
            String line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);
            int index = 0;
            while (tokenizer.hasMoreTokens()) {
                histogram[index++] = Double.parseDouble(tokenizer.nextToken());
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
                int[] pixel = image.getPixel(i, j);
                int r = pixel[0];
                int g = pixel[1];
                int b = pixel[2];
                histogram[(r << (2 * this.bitDepth)) + (g << this.bitDepth) + b]++;
            }
        }
    }
}
