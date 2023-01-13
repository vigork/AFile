package team.iks.afile.pojo.vo;

import java.time.LocalDateTime;

import team.iks.afile.enumerate.FileTypeEnum;

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
public class FileItemVO {
    private String name;
    private String path;
    private FileTypeEnum type;
    private Long size;
    private LocalDateTime modifyTime;
}
