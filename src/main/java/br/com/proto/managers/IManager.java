package br.com.proto.managers;

import java.util.List;

public interface IManager {

    <T> T create(final String name, final Object object);
    <T> T read(final String id);
    <T> List<T> getList();
    boolean delete(final Object object);
    boolean delete(final String id);
    boolean update(final Object object);


}
