public class textFileResultPair {
    private final String fileName;
    private final double result;

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
}