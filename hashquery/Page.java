public class Page {
    private Record[] records;
    private int recordCount;

    public Page(int recordSlots) {
        this.records = new Record[recordSlots];
        this.recordCount = 0;
    }

	// Insert a given record into the page
    public int insertRecord(Record rec) {
        int count = 0;
        for (Record el : records ) {
            if (el != null) {
                count++;
            } else {
                records[count] = rec;
                recordCount++;
                return count;
            }
        }
        return -1;
    }

    public Record getRecord(int slotNumber) {
        return records[slotNumber];
    }

    public int getRecordCount() {
        return recordCount;
    }
}
