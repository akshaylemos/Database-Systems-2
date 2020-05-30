import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class hashLoad{

    public static void main(String[] args) {

        int hashMode=1000;
        int pagesize =0;

        Scanner scanner=new Scanner(System.in);
        System.out.println("Input page size, ex: hashload 4096");
        // Initialise pagesize from command line argument variables
        if (scanner.next().equalsIgnoreCase("hashload")) {
            try {
                pagesize = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.err.println("Error! pagesize value must be an integer.");
            }
        }  else {
            System.err.println("Error! Usage: java hashload pagesize");
        }

        // Initialise recordSize (calculated manually) and set starting time for timing purposes.
        final int recordSize = 368;
        long startTime = System.nanoTime();

        // Import a new page from the file and search each record
        HeapDataImporter importer = new HeapDataImporter(pagesize, recordSize);
        // Create the data exporter and export objects
        HashDataExporter exporter = new HashDataExporter(pagesize);
        //initialize the buckets
        Bucket[] buckets=new Bucket[hashMode];
        for(int i=0;i<hashMode;i++){
            buckets[i]=new Bucket(10000);
        }
        //get newPage to get bucket lines
        Page newPage = importer.readPageFromFile();
        int pageCount=0;
        while(newPage != null) {
            int pageOffset=0;
            for (int i = 0; i < newPage.getRecordCount(); i++) {
                Record rec = newPage.getRecord(i);
                String bName=rec.getBuilding_name();
                if(bName!=" " && bName!=null){
                    int hashCode=Math.abs(rec.getBuilding_name().trim().hashCode())%hashMode;
                    Line line=new Line(hashCode,pageCount,pageOffset);
                    buckets[hashCode].insertLine(line);
                    pageOffset++;
                }
            }
            newPage = importer.readPageFromFile();
            pageCount++;
        }
        exporter.writeBucketsToFile(buckets);

        // Calculate total running time
        long totalTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        System.out.println("Time taken to load hash file:     " + totalTime + "ms");
    }
}
