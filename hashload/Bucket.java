public class Bucket {
    // Define class attributes
    private int bucketSize;
    private Line[] lines;
    private int lineCount;

    public Bucket(int lineSlots) {
        bucketSize=120000;
        this.lines = new Line[lineSlots];
        lineCount=0;
    }

	// Insert a given line into the bucket
    public int insertLine(Line line) {
        int count = 0;
        for (Line el : lines ) {
            if (el != null) {
                count++;
            } else {
                lines[count] = line;
                lineCount++;
                return count;
            }
        }
        return -1;
    }

    public Line[] getLines() {
        return lines;
    }

    public Line getLine(int slotNumber) {
        return lines[slotNumber];
    }

    public int getLineCount() {
        lineCount=Math.min(lineCount,9998);
        return lineCount;
    }

    public int getBucketSize() {
        return bucketSize;
    }
}
