
public class Bucket {
    private Line[] lines;
    private int lineCount;

    public Bucket(int lineSlots) {
        this.lines = new Line[lineSlots];
        this.lineCount=0;
    }

	// Insert a given line into the bucket
    public int insertLine(Line line) {
        int count = 0;
        for (Line el : lines ) {
            if (el != null) {
                count++;
            } else {
                lines[count] = line;
                this.lineCount++;
                return count;
            }
        }
        return -1;
    }

    public Line[] getLines() {
        return this.lines;
    }

    public Line getLine(int slotNumber) {
        return lines[slotNumber];
    }

    public int getLineCount() {
        return this.lineCount;
    }

}
