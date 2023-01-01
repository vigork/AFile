package team.iks.afile.storage;

/**
 * TODO: description
 *
 * @author vigork
 * At: 2023/1/1
 */
public abstract class AbstractStorage<T extends StorageConfig> implements Readable, Writeable {
    protected final T config;

    protected AbstractStorage(T config) {
        this.config = config;
    }
}
