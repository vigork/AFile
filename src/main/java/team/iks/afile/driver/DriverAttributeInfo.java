package team.iks.afile.driver;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 配置项信息
 *
 * @author vigork
 * At: 2023/1/2
 */
@Data
@Accessors(chain = true)
public class DriverAttributeInfo {
    private String label;
    private String name;
    private String description;
    private Boolean required;
    private Integer order;
}
