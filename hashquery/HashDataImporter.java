import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

public class HashDataImporter {

    // Define class attributes
    int hashIndex;
    int bucketCount=1000;
    int bucketSize=120000;
    int pagesize;
    private DataInputStream in;

    // Create the DataInputStream in the class constructor
    public HashDataImporter(int pagesize, int hashIndex) {
        this.hashIndex=hashIndex;
        this.pagesize=pagesize;
        try {
            this.in = new DataInputStream(new FileInputStream("hash." + Integer.toString(pagesize)));
        } catch(FileNotFoundException e) {
            System.err.println("Error! Hash file was not found.");
        }
    }

    // Read bucket and return Bucket
    public Bucket readBucketFromFile() {
        Bucket bucket = new Bucket((bucketCount));
        byte[] data = new byte[bucketSize];
        try {
            //skip bytes to get expected data
            in.skipBytes(bucketSize*hashIndex);
            int dataRead = in.read(data, 0, bucketSize);
            if (dataRead == -1) {
                return null;
            }
            int lineCount = new BigInteger(Arrays.copyOfRange(data, bucketSize - 4, bucketSize)).intValue();
            for (int i = 0; i < lineCount; i++) {
                byte[] lineData = Arrays.copyOfRange(data, i * 12, (i + 1) * 12);
                bucket.insertLine(parseLine(lineData));
            }

        } catch (IOException e) {
            System.err.println("Error! Failed to read from heap file.");
        }
        return bucket;
    }

    // Take byte array for individual line and turn the bytes back into integers
    private Line parseLine(byte[] recordData) {
        int hashIndex = new BigInteger(Arrays.copyOfRange(recordData, 0, 4)).intValue();
        int pageId = new BigInteger(Arrays.copyOfRange(recordData, 4, 8)).intValue();
        int pageOffset = new BigInteger(Arrays.copyOfRange(recordData, 8, 12)).intValue();
        return new Line(hashIndex, pageId,pageOffset);
    }

}
