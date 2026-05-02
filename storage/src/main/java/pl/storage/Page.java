package pl.storage;
import java.nio.ByteBuffer;
public class Page {
    private int pageId = 1;
    private int pinCount = 0;
    private boolean isDirty = false;
    private final  ByteBuffer data = ByteBuffer.allocateDirect(DiscManager.PAGE_SIZE);

    public int getPaidId(){return pageId;}
    public void setPageId(int pageId) {this.pageId = pageId; }

    public int getPinCount(){return pinCount;}
    public void incrementPin() {pinCount++;}
    public void decrementPin() {if (pinCount > 0) pinCount--;}

    public boolean isDirty() {return isDirty;}
    public void setDirty(boolean dirty) {isDirty = dirty;}

    public ByteBuffer getData() {return data;}

    public void reset(){
        pageId = -1;
        pinCount = 0;
        isDirty = false;
        data.clear();
    }
}
