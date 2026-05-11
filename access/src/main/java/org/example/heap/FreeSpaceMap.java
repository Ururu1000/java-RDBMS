package org.example.heap;

import org.example.config.Config;

public class FreeSpaceMap {
    /**
     * 8 бит = 1 байт (256 число/ 0 - 255)
     * int 4 байта (2^31 - 1)
     * 1 елемент массива = 1 байт
     * индекс байта = номер страницы
     *
     * 0 страница (хеадер) описывает остальные,
     * файл - 4096 байт
     * 8 байт - заголовок (0-3 байта это инт numDataPages, 4-8 байта это версия формата или другие системные флаги)
     * 4088 - байт ФСМ масива 4088 страниц данных
     **/

    private static final int HEADER_META_SIZE = 8;  // int numPages (4) + int version
    private static final int MAX_FSM_VALUE = 255;
    private final byte[] pageData;  // данные Header Page

    FreeSpaceMap(byte[] pageData) {
        this.pageData = pageData;
    }

    public void setFreeSpace(int dataPageIdx, int freeBytes){
        // Метод берёт реальное количество свободных байтов,
        // сжимает их по формуле в число 0-255 и записывает в ячейку

        // 1. Считаем пропорцию, кастуя один из операндов в double для точности
        int encoded = (int) ((freeBytes / (double) Config.PAGE_SIZE) * MAX_FSM_VALUE);

        // 2. Защита: жестко фиксируем значение в безопасном диапазоне 0-255
        encoded = (int) Math.min(MAX_FSM_VALUE, Math.max(0, encoded));

        // 3. Сохраняем в массив.
        // Java bytes хранит данные от -128 до 127
        pageData[HEADER_META_SIZE + dataPageIdx] = (byte) encoded;
    }

    public int getFreeSpace(int dataPageIdx){
        int decoded = (int) pageData[HEADER_META_SIZE + dataPageIdx] & 0xFF;
        return (int) ((decoded/MAX_FSM_VALUE) * Config.PAGE_SIZE);
    }

    public int findPageWithSpace(int requiredBytes){
        int numPages = getNumDataPages();

        for(int i = 0; i < numPages; i++){
            if(getFreeSpace(i) >= requiredBytes){
                return i;
            }
        }
        return -1; // -1 означает "места нет, HeapFile должен создать новую страницу"
    }


    // Возвращает максимальное количество страниц
    public  static int maxPagesInFSM() {
        return Config.PAGE_SIZE - HEADER_META_SIZE;
    }

    // Переводит 4 отдельных байта в число типа инт (4 байта)
    private int getNumDataPages(){
        return (pageData[0] & 0xFF) << 24
                | (pageData[1] & 0xFF) << 16
                | (pageData[2] & 0xFF) << 8
                | (pageData[3] & 0xFF);
    }

    //переводит инт в 4 отдельных последовательных байта
    private void writeInt(int offset, int value) {
        pageData[offset] = (byte) ((value >> 24) & 0xFF);
        pageData[offset + 1] = (byte) ((value >> 16) & 0xFF);
        pageData[offset + 2] = (byte) ((value >> 8) & 0xFF);
        pageData[offset + 3] = (byte) (value & 0xFF);
    }
}
