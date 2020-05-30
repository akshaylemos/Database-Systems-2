import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HashDataExporter {
    // Define class attributes
    private DataOutputStream out;
    private int lineSize;
    private int pagesize;
    // Create the DataOutputStream in the class constructor
    public HashDataExporter(int pagesize) {
        // Initialise class attributes and create DataOutputStream to write to file
        this.pagesize=pagesize;
        this.lineSize=12;
        try {
            this.out = new DataOutputStream(new FileOutputStream("hash." + Integer.toString(this.pagesize)));
        } catch(FileNotFoundException e) {
            System.err.println("Error! Output file not found.");
        }
    }
    // write the bucket data to the file
    public int writeBucketsToFile(Bucket[] buckets) {
        try {
            // Loops through each bucket, writing the data to the hash file
            for (int i = 0; i < buckets.length; i++) {
                for(int j=0;j<buckets[i].getLineCount();j++){
                    Line line = buckets[i].getLine(j);
                    out.writeInt(line.getHashIndex());
                    out.writeInt(line.getPageId());
                    out.writeInt(line.getPageOffset());
                }
                // Once all lines have been written, pad the rest of the bucket size with zeros
                // leaving 4 bytes at the end to store an int - number of lines on the bucket
                for (int j = 0; j < (buckets[i].getBucketSize() - 4) - (buckets[i].getLineCount()*lineSize); j++) {
                    out.writeByte(0);
                }
                //System.out.println(i);
                out.writeInt(buckets[i].getLineCount());
            }
        } catch(IOException e) {
            System.err.println("Error! Failed to write record to heap file.");
        }
        return 1;
    }
}
