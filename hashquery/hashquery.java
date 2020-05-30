import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class hashquery {

    public static void main(String[] args) {
        int hashMode=1000;
        Scanner scanner=new Scanner(System.in);
        // Define variables to store command line arguments
        System.out.println("Input search text, ex: Gawha");
        String textQuery = scanner.nextLine().trim();
        int searchIndex=Math.abs(textQuery.hashCode())%hashMode;
        int pagesize=0;
        try {
            System.out.println("Input page size, ex: 4096");
            pagesize = Integer.valueOf(scanner.nextLine());
        } catch(NumberFormatException e) {
            System.err.println("Error! pagesize value must be an integer.");
        }

		// Set record size and initialise timer
        final int recordSize = 368;
        long startTime = System.nanoTime();

		// Initialise counter for matching results and boolean for end of file
        int totalMatchingResults = 0;

		// Import a new page from the heap file and import a bucket from the hash file
        HeapDataImporter importer = new HeapDataImporter(pagesize, recordSize);
        HashDataImporter hashImporter = new HashDataImporter(pagesize,searchIndex);
        //initialize the pages
        Page[] pages=new Page[100000];
        //get a bucket from the hash file
        Bucket bucket=hashImporter.readBucketFromFile();
        //search and display the record using bucket lines
        for(int i=0;i<bucket.getLineCount();i++){
            int pageId=bucket.getLine(i).getPageId();
            int pageOffset=bucket.getLine(i).getPageOffset();
            if(pages[pageId]==null){
                Page page=importer.readPageFromFile(pageId);
                pages[pageId]=page;
            }
            Record record=pages[pageId].getRecord(pageOffset);
            if(record.getBuilding_name().equals(textQuery)){
                System.out.println(record.print());
                totalMatchingResults++;
            }
        }

		// Finalise the running time
        long totalTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

		// Handle output of running time and number of results
        if (totalMatchingResults == 0) {
            System.out.println("No matching results found.");
			System.out.println("Time taken to search heap file: " + totalTime + "ms");
        } else {
            System.out.println("\nTotal matches found:          " + totalMatchingResults);
            System.out.println("Time taken to search heap file: " + totalTime + "ms");
        }
    }
}
