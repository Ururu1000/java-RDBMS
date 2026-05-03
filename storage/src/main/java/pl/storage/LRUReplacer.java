package pl.storage;
import java.util.LinkedHashSet;
public class LRUReplacer {
    private final LinkedHashSet<Integer> unpinnedFrames;
    private final int capacity;

    public LRUReplacer(int numPages){
        this.capacity = numPages;
        this.unpinnedFrames = new LinkedHashSet<>(numPages);
    }
    public synchronized void pin(int frameId){
        unpinnedFrames.add(frameId);
    }
    public synchronized void unpin(int frameId){
        if(unpinnedFrames.size () < capacity){
            unpinnedFrames.add(frameId);
        }
    }
    public synchronized Integer victim() {
        if (unpinnedFrames.isEmpty()) {
            return null;
        }
        int victimId = unpinnedFrames.iterator().next();
        unpinnedFrames.remove(victimId);
        return victimId;
    }
}
