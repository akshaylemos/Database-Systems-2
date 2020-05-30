public class Record {
    private int census_year;
    private int block_id;
    private int property_id;
    private int base_property_id;
    private String building_name;
    private String street_address;
    private String clue_small_area;
    private int construction_year;
    private int refurbished_year;
    private int number_of_floors;
    private String predominant_space_use;
    private String accessibility_type;
    private String accessibility_type_description;
    private int accessibility_rating;
    private int bicycle_spaces;
    private int has_showers;
    private float x_coordinate;
    private float y_coordinate;
    private String location;

    public Record(int census_year, int block_id, int property_id, int base_property_id, String building_name, String street_address, String clue_small_area, int construction_year, int refurbished_year, int number_of_floors, String predominant_space_use, String accessibility_type, String accessibility_type_description, int accessibility_rating, int bicycle_spaces, int has_showers, float x_coordinate, float y_coordinate, String location) {
        this.census_year = census_year;
        this.block_id = block_id;
        this.property_id = property_id;
        this.base_property_id = base_property_id;
        this.building_name = building_name;
        this.street_address = street_address;
        this.clue_small_area = clue_small_area;
        this.construction_year = construction_year;
        this.refurbished_year = refurbished_year;
        this.number_of_floors = number_of_floors;
        this.predominant_space_use = predominant_space_use;
        this.accessibility_type = accessibility_type;
        this.accessibility_type_description = accessibility_type_description;
        this.accessibility_rating = accessibility_rating;
        this.bicycle_spaces = bicycle_spaces;
        this.has_showers = has_showers;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.location = location;
    }

    public int getCensus_year() {
        return census_year;
    }

    public int getBlock_id() {
        return block_id;
    }

    public int getProperty_id() {
        return property_id;
    }

    public int getBase_property_id() {
        return base_property_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public String getStreet_address() {
        return street_address;
    }

    public String getClue_small_area() {
        return clue_small_area;
    }

    public int getConstruction_year() {
        return construction_year;
    }

    public int getRefurbished_year() {
        return refurbished_year;
    }

    public int getNumber_of_floors() {
        return number_of_floors;
    }

    public String getPredominant_space_use() {
        return predominant_space_use;
    }

    public String getAccessibility_type() {
        return accessibility_type;
    }

    public String getAccessibility_type_description() {
        return accessibility_type_description;
    }

    public int getAccessibility_rating() {
        return accessibility_rating;
    }

    public int getBicycle_spaces() {
        return bicycle_spaces;
    }

    public int getHas_showers() {
        return has_showers;
    }

    public float getX_coordinate() {
        return x_coordinate;
    }

    public float getY_coordinate() {
        return y_coordinate;
    }

    public String getLocation() {
        return location;
    }

	// Returns the record in string format to be printed if there is a match
    public String print() {
        return intToString(census_year) + "," +
                intToString(block_id) + "," +
                intToString(property_id) + "," +
                intToString(base_property_id) + "," +
                building_name + "," +
                street_address + "," +
                clue_small_area + "," +
                intToString(construction_year) + "," +
                intToString(refurbished_year) + "," +
                intToString(number_of_floors) + "," +
                predominant_space_use + "," +
                accessibility_type + "," +
                accessibility_type_description + "," +
                intToString(accessibility_rating) + "," +
                intToString(bicycle_spaces) + "," +
                boolIntToString(has_showers) + "," +
                floatToString(x_coordinate) + "," +
                floatToString(y_coordinate) + "," +
                location;

    }

	// Convert an int to a string and print null if the value is -1
    private String intToString(int value) {
        if (value == -1) { return "null"; }
        return Integer.toString(value);
    }
	// Convert an float to a string and print null if the value is -1
    private String floatToString(float value) {
        if (value == -1) { return "null"; }
        return Float.toString(value);
    }
	// Convert an boolean to a string and print null if the value is -1
    private String boolIntToString(int value) {
        if (value == 1) { return "TRUE"; }
        else if (value == 0) { return "FALSE"; }
        return "null";
    }
}
