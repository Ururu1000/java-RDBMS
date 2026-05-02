package pl.storage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DiscManager implements AutoCloseable {
    public static final int PAGE_SIZE = 4096;
    private final FileChannel dbFile;

    public DiscManager(Path dbPath) throws IOException {
        this.dbFile = FileChannel.open(dbPath,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.DSYNC);
    }
    public void writePage(int PageId, ByteBuffer buffer) throws IOException {
        long offset = PageId * PAGE_SIZE;
        buffer.rewind();
        dbFile.read(buffer, offset);
    }
    public void readPage(int PageId, ByteBuffer buffer) throws IOException {
        long offset = (long) PageId * PAGE_SIZE;
        buffer.clear();
        dbFile.read(buffer, offset);
    }
    @Override
    public void close() throws IOException {
        if (dbFile.isOpen()) dbFile.close();
    }
    // возвращает текущее кол во страниц в файле БД
    public int getNumPages() throws IOException{
        return(int) (dbFile.size() / PAGE_SIZE);
    }
    // выделяет новую страницу в конце файла и возвращает ее ID
    public int allocatePage() throws IOException {
        int newPageId = getNumPages();

        //пишем пустую страницу чтобы ОС выделила блоки на диске
        ByteBuffer emptyBuffer = ByteBuffer.allocateDirect(PAGE_SIZE);
        writePage(newPageId, emptyBuffer);
        return newPageId;
    }
}
