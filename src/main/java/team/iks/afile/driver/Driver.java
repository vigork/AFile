package team.iks.afile.driver;

import java.util.List;

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
public class Driver {
    /**
     * 驱动名称
     */
    private String name;

    /**
     * 驱动器
     */
    private Class<? extends AbstractDriver<?>> driver;

    /**
     * 配置项信息
     */
    private List<DriverAttribute> attributes;

    /**
     * 排序
     */
    private Integer order;
}