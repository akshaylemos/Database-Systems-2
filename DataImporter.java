import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;

public class DataImporter {

    // Define class attributes
    private int pagesize;
    private int recordSize;
    private DataInputStream in;

    // Create the DataInputStream in the class constructor
    public DataImporter(int pagesize, int recordSize) {
        this.pagesize = pagesize;
        this.recordSize = recordSize;
        try {
            this.in = new DataInputStream(new FileInputStream("heap." + Integer.toString(pagesize)));
        } catch(FileNotFoundException e) {
            System.err.println("Error! Heap file was not found.");
        }
    }

    // Read pagesize bytes and check how many records exist, then parse that amount of records
    public Page readPageFromFile() {
        Page page = new Page();
        //temp variable for storing records
        Hashtable records=new Hashtable();
        byte[] data = new byte[pagesize];
        try {
            int dataRead = in.read(data, 0, pagesize);
            if (dataRead == -1) {
                return null;
            }
            int recordCount = new BigInteger(Arrays.copyOfRange(data, pagesize - 3, pagesize)).intValue();
            for (int i = 0; i < recordCount; i++) {
                byte[] recordData = Arrays.copyOfRange(data, i * recordSize, (i + 1) * recordSize);
                Record record=parseRecord(recordData);
                //calculate hashIndex of record
                int hashIndex=makeRecordHashIndex(record);
                //get the corresponding recordList from records
                List<Record> recordList= (List<Record>) records.get(hashIndex);
                if(recordList==null){
                    recordList=new ArrayList<>();
                }
                //add record in the recordList
                recordList.add(record);
                //update the record's value
                records.put(hashIndex,recordList);
            }
        } catch (IOException e) {
            System.err.println("Error! Failed to read from heap file.");
        }
        //set records to the page
        page.setRecords(records);
        return page;
    }

    // Take byte array for individual records and turn the bytes back into strings and integers
    private Record parseRecord(byte[] recordData) {
        int census_year = new BigInteger(Arrays.copyOfRange(recordData, 0, 4)).intValue();
        int block_id = new BigInteger(Arrays.copyOfRange(recordData, 4, 8)).intValue();
        int property_id = new BigInteger(Arrays.copyOfRange(recordData, 8, 12)).intValue();
        int base_property_id = new BigInteger(Arrays.copyOfRange(recordData, 12, 16)).intValue();
        String building_name = trimNulls(new String(Arrays.copyOfRange(recordData, 16, 81)));
        String street_address = trimNulls(new String(Arrays.copyOfRange(recordData, 81,116)));
        String clue_small_area = trimNulls(new String(Arrays.copyOfRange(recordData, 116, 146)));
        int construction_year = new BigInteger(Arrays.copyOfRange(recordData, 146, 150)).intValue();
        int refurbished_year = new BigInteger(Arrays.copyOfRange(recordData, 150, 154)).intValue();
        int number_of_floors = new BigInteger(Arrays.copyOfRange(recordData, 154, 158)).intValue();
        String predominant_space_use = trimNulls(new String(Arrays.copyOfRange(recordData, 158, 198)));
        String accessibility_type = trimNulls(new String(Arrays.copyOfRange(recordData, 198, 233)));
        String accessibility_type_description = trimNulls(new String(Arrays.copyOfRange(recordData, 233, 318)));
        int accessibility_rating = new BigInteger(Arrays.copyOfRange(recordData, 318, 322)).intValue();
        int bicycle_spaces = new BigInteger(Arrays.copyOfRange(recordData, 322, 326)).intValue();
        int has_showers = new BigInteger(Arrays.copyOfRange(recordData, 326, 330)).intValue();
        float x_coordinate = ByteBuffer.wrap(Arrays.copyOfRange(recordData, 330, 334)).getFloat();
        float y_coordinate = ByteBuffer.wrap(Arrays.copyOfRange(recordData, 334, 338)).getFloat();
        String location = trimNulls(new String(Arrays.copyOfRange(recordData, 338, 368)));
        return new Record(census_year, block_id, property_id, base_property_id, building_name, street_address, clue_small_area,
                construction_year, refurbished_year, number_of_floors, predominant_space_use, accessibility_type, accessibility_type_description,
                accessibility_rating, bicycle_spaces, has_showers, x_coordinate, y_coordinate, location);
    }

    // Trim any null bytes from the strings due to the fixed length records
    private String trimNulls(String str) {
        int pos = str.indexOf(0);
        return pos == -1 ? str : str.substring(0, pos);
    }
    //make the hashIndex of record
    private int makeRecordHashIndex(Record record){
        int recordCount=pagesize/recordSize;
        int hashCode=record.getBuilding_name().hashCode()%(recordCount);
        return hashCode<0?hashCode+recordCount:hashCode;
    }
}
