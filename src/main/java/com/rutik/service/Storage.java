package com.rutik.service;

import java.io.IOException;
import java.util.List;

public interface Storage<T>  {
    void reset() throws IOException;
    void append(T obj) throws IOException;
    List<T> load() throws IOException;
}
