package team.iks.afile.driver;

/**
 * 驱动器
 *
 * @author vigork
 * At: 2023/1/1
 */
public abstract class AbstractDriver<T extends DriverAttributes> implements Readable, Writeable {
    protected final T config;

    public AbstractDriver(T config) {
        this.config = config;
    }
}
