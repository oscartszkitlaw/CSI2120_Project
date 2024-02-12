import java.io.File;
import java.util.PriorityQueue;

public class SimilaritySearch {

    private static void addAmount(PriorityQueue<textFileResultPair> priorityQueue, textFileResultPair pair) {
        priorityQueue.offer(pair);

        // Enforce the max size constraint
        while (priorityQueue.size() > 5) {
            priorityQueue.poll();
        }
    }

    public static void main(String[] args) {

        String queryString = args[0];
        String datasetFolder = args[1];

        // Load the query image and compute its histogram
        ColorImage queryImage = new ColorImage("queryImages/" + queryString);
        queryImage.reduceColor(3);
        ColorHistogram queryHistogram = new ColorHistogram(3);
        queryHistogram.setImage(queryImage);

        PriorityQueue<textFileResultPair> minAmountPriorityQueue = new PriorityQueue<>((pair1, pair2) -> Double.compare(pair1.getResult(), pair2.getResult()));

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

        // Retrieve and print the highest 5 amounts
        while (!minAmountPriorityQueue.isEmpty()) {
            System.out.println(minAmountPriorityQueue.poll().getFileName());
        }
    }

}
