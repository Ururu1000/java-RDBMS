package pl.rdbms.storage.buffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class BufferPoolManager {
    private final int poolSize;
    private final Page[] pages;
    private final DiskManager diskManager;
    private final Replacer replacer;

    private final Map<Integer, Integer> pageTable;
    private final Queue<Integer> freelist;
    private final ReentrantLock lock;
    public BufferPoolManager(int poolSize, DiskManager diskManager,Replacer replacer ){
        this.poolSize = poolSize;
        this.pages = new Page[poolSize];
        this.diskManager = diskManager;
        this.replacer = replacer;
        this.pageTable = new ConcurrentHashMap<>();
        this.freelist = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
            for(int i = 0; i < poolSize; i++){
                freelist.add(i);
                pages[i] = new Page();
            }
    }
}
