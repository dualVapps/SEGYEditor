package main.java.vladimir.seis.segystream.SEGYTempEdit;

/*
 * Класс который записываютя данные (данные трасс) из прочитанного файла
 * для выполнения различных операций, содержит
 * функцию для записи данных */


import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SegyTempTraceData {

    public float[] data;

    public void writeToDataOutputStream(DataOutputStream dos, int traceLenthInBytes) throws IOException {

        ByteBuffer bb = ByteBuffer.allocate(traceLenthInBytes);
        bb.order(ByteOrder.BIG_ENDIAN);
        for (int i = 0; i < data.length; i++) {
            bb.putFloat(data[i]);
        }

        dos.write(bb.array());
    }

    public float[] getData() {
        return data;
    }
}
