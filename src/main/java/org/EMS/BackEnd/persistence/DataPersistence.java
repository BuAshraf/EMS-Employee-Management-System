package org.EMS.BackEnd.persistence;

import java.io.IOException;
import java.util.List;

public interface DataPersistence {
    List<List<Object>> readData(String range) throws IOException;

    void writeData(String range, List<List<Object>> values) throws IOException;
}
