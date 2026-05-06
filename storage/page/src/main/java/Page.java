import static pl.rdbms.common.Config.PAGE_SIZE;

public class Page {
    private int pageId = -1;
    private int pinCount = 0;
    private boolean isDirty = false;
    private final byte[] data = new byte[PAGE_SIZE];
    public int getPaidId(){return pageId;}
    public void setPageId(int pageId) {this.pageId = pageId; }

    public int getPinCount(){return pinCount;}
    public void incrementPin() {pinCount++;}
    public void decrementPin() {if (pinCount > 0) pinCount--;}

    public boolean isDirty() {return isDirty;}
    public void setDirty(boolean dirty) {isDirty = dirty;}

    public byte[] getData() {return data;}

    public void reset(){
        pageId = -1;
        pinCount = 0;
        isDirty = false;
        java.util.Arrays.fill(data,(byte)0);
    }
}