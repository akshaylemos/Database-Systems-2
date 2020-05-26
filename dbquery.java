import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class dbquery {

    public static void main(String[] args) {

        // Define variables to store command line arguments
        String textQuery = "";
        int pagesize = 0;

		// Set record size and initialise timer
        final int recordSize = 368;
        long startTime = System.nanoTime();

		// Handle the command line arguments
        if (args.length != 2) {
            System.err.println("Error! Usage: java dbquery text pagesize");
        } else {
            textQuery = args[0];
            try {
                pagesize = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                System.err.println("Error! pagesize value must be an integer.");
            }
        }
        //calculate the hashIndex of textQuery for search
        int searchIndex=textQuery.hashCode()%(pagesize/recordSize);
        searchIndex=searchIndex<0?searchIndex+(pagesize/recordSize):searchIndex;
		// Initialise counter for matching results and boolean for end of file
        int totalMatchingResults = 0;
		// Import a new page from the file and search each record for the search text
        DataImporter importer = new DataImporter(pagesize, recordSize);
        Page newPage = importer.readPageFromFile();
        while(newPage != null) {
            //get all records of page
            Hashtable records=newPage.getRecords();
            //get exact recordList from all records
            List<Record> recordList= (List<Record>) records.get(searchIndex);
            if(recordList==null){
                newPage = importer.readPageFromFile();
                continue;
            }
            //identify that the each record in recordList is expected record
            for(int i=0;i<recordList.size();i++){
                if(recordList.get(i).getBuilding_name().equals(textQuery)){
                    System.out.println(recordList.get(i).print());
                    totalMatchingResults++;
                }
            }
            newPage = importer.readPageFromFile();
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
