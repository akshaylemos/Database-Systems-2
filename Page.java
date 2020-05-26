import java.util.Hashtable;

public class Page {
    //here use hashtable instead of Record[] in Assignment 1
    private Hashtable records;
    //constructor
    public Page() {
        records= new Hashtable();
    }
    //getter of records
    public Hashtable getRecords() {
        return this.records;
    }
    //setter of records
    public void setRecords(Hashtable hashtable) {
        this.records=hashtable;
    }
}
