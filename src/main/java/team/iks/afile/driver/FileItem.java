package team.iks.afile.driver;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TODO: description
 *
 * @author vigork
 * At: 2023/1/1
 */
@Data
@Accessors(chain = true)
public class FileItem {
    private String name;
    private String path;
    private FileTypeEnum type;
    private Long size;
    private LocalDateTime modifyTime;
}
