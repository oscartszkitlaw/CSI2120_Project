/*
 * Group Members: Charley Liu, Oscar (Tsz Kit) Law
 * Student IDs: 300304744, 300306180
 */

import java.io.File;
import java.util.PriorityQueue;

public class SimilaritySearch {
    /**
     * The main method for the program
     * 
     * @param args[0] the query image
     * @param args[1] the dataset folder
     */
    public static void main(String[] args) {
        try {
            // Parse command line arguments
            String queryString = args[0];
            String datasetFolder = args[1];

            // Load the query image and compute its histogram
            ColorImage queryImage = new ColorImage("queryImages/" + queryString);
            queryImage.reduceColor(3);
            ColorHistogram queryHistogram = new ColorHistogram(3);
            queryHistogram.setImage(queryImage);

            PriorityQueue<TextFileResultPair> minAmountPriorityQueue = new PriorityQueue<>();

            System.out.println("Query Image: " + queryString);
            System.out.println("Image Dataset: " + datasetFolder);
            System.out.println("Searching for the 5 most similar images...\n");

            // Loop through all text files in dataset and compare with query image
            File folder = new File(datasetFolder);
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    String fileName = file.getName();

                    // compute the image's histogram and comparison result with the query image
                    ColorHistogram comparisonHistogram = new ColorHistogram(datasetFolder + "/" + fileName);
                    double comparisonResult = queryHistogram.compare(comparisonHistogram);

                    // Check if the result can be added to the priority queue of the 5 most similar images
                    TextFileResultPair fileResultPair = new TextFileResultPair(fileName, comparisonResult);
                    if (minAmountPriorityQueue.size() < 5 || minAmountPriorityQueue.peek().getResult() < fileResultPair.getResult()) {
                        if (minAmountPriorityQueue.size() >= 5) {
                            minAmountPriorityQueue.poll();
                        }

                        minAmountPriorityQueue.offer(fileResultPair);
                    }
                }
            }

            // Retrieve and print the 5 most similar images
            int rank = minAmountPriorityQueue.size();
            while (!minAmountPriorityQueue.isEmpty()) {
                TextFileResultPair res = minAmountPriorityQueue.poll();
                System.out.println("#" + (rank--) + " --> Image: " + res.getFileName() + ", Similarity: " + res.getResult());
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Usage: java SimilaritySearch [ppm_filename] [dataset_folder]");
        }
    }

}
