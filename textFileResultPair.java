public class textFileResultPair implements Comparable<textFileResultPair> {
    private String fileName;
    private double result;

    public textFileResultPair(String fileName, double result) {
        this.fileName = fileName;
        this.result = result;
    }

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
    public int compareTo(textFileResultPair o) {
        if (this.getResult() > o.getResult()) {
            return 1;
        }
        else if (this.getResult() < o.getResult()) {
            return -1;
        }
        return 0;
    }
}