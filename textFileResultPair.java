/*
 * Group Members: Charley Liu, Oscar (Tsz Kit) Law
 * Student IDs: 300304744, 300306180
 */

public class TextFileResultPair implements Comparable<TextFileResultPair> {
    private String fileName;
    private double result;

    /**
     * Constructs a TextFileResultPair instance 
     * 
     * @param fileName the name of the file
     * @param result the result of comparison
     */
    public TextFileResultPair(String fileName, double result) {
        this.fileName = fileName;
        this.result = result;
    }

    // Getter methods
    public String getFileName() {
        return this.fileName;
    }
    public double getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        return this.fileName + "," + this.result;
    }
    
    @Override
    public int compareTo(TextFileResultPair o) {
        if (this.getResult() > o.getResult()) {
            return 1;
        }
        else if (this.getResult() < o.getResult()) {
            return -1;
        }
        return 0;
    }
}