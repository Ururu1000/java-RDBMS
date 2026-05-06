package pl.rdbms.storage.buffer;

public interface Replacer {
    void pin(int frameId);
    void unpin(int frameId);
    Integer victim();
}
