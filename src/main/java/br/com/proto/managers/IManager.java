package br.com.proto.managers;

public interface IManager {

    boolean create(Object object);
    boolean update(Object object);

    <T> T read(final String id);
    boolean delete(final String id);
}
