public class Line {
    private int hashIndex;
    private int pageId;
    private int pageOffset;

    public Line(int hashIndex,int pageId,int pageOffset) {
        this.hashIndex=hashIndex;
        this.pageId=pageId;
        this.pageOffset=pageOffset;
    }

    public int getHashIndex(){
        return this.hashIndex;
    }

    public int getPageId() {
        return pageId;
    }

    public int getPageOffset() {
        return pageOffset;
    }

}
