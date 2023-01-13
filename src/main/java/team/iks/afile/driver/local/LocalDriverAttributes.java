package team.iks.afile.driver.local;

import team.iks.afile.driver.DriverAttributes;
import team.iks.afile.driver.DriverAttributeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * LocalDriver 配置信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LocalDriverAttributes extends DriverAttributes {
    @DriverAttributeInfo(label = "根路径", required = true)
    private String rootPath;
}
