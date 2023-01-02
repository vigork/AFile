package team.iks.afile.driver;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 存储库驱动信息
 *
 * @author vigork
 * At: 2023/1/2
 */
@Data
@Accessors(chain = true)
public class DriverInfo {
    /**
     * 驱动名称
     */
    private String name;

    /**
     * 驱动器
     */
    private Class<? extends AbstractDriver<?>> driver;
}
