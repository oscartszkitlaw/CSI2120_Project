import java.io.File;
import java.util.PriorityQueue;

public class SimilaritySearch {

    private static void addAmount(PriorityQueue<textFileResultPair> priorityQueue, textFileResultPair pair) {
        if (priorityQueue.size() < 5 || priorityQueue.peek().getResult() < pair.getResult()) {
            if (priorityQueue.size() >= 5) {
                priorityQueue.poll();
            }
            priorityQueue.offer(pair);
        }
    }

    public static void main(String[] args) {
        try {
            String queryString = args[0];
            String datasetFolder = args[1];

            // Load the query image and compute its histogram
            ColorImage queryImage = new ColorImage("queryImages/" + queryString);
            ColorHistogram queryHistogram = new ColorHistogram(3);
            queryHistogram.setImage(queryImage);

            PriorityQueue<textFileResultPair> minAmountPriorityQueue = new PriorityQueue<>();

            System.out.println("Query Image: " + queryString);
            System.out.println("Image Dataset: " + datasetFolder);
            System.out.println("Searching for the 5 most similar images...\n");

            // Loop through all text files in dataset and compare with query image
            File folder = new File(datasetFolder);
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    String fileName = file.getName();
                    ColorHistogram comparisonHistogram = new ColorHistogram(datasetFolder + "/" + fileName);
                    double comparisonResult = queryHistogram.compare(comparisonHistogram);

                    textFileResultPair fileResultPair = new textFileResultPair(fileName, comparisonResult);
                    addAmount(minAmountPriorityQueue, fileResultPair);
                }
            }

            // // Retrieve and print the highest 5 amounts
            int rank = 5;
            while (!minAmountPriorityQueue.isEmpty()) {
                textFileResultPair res = minAmountPriorityQueue.poll();
                System.out.println("#" + (rank--) + " --> Image: " + res.getFileName() + ", Similarity: " + res.getResult());
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Usage: java SimilaritySearch [ppm_filename] [dataset_folder]");
        }
    }

}
