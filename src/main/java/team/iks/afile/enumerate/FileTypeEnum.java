package team.iks.afile.enumerate;

/**
 * 文件类型枚举类
 *
 * @author vigork
 * At: 2023/1/1
 */
public enum FileTypeEnum {
    OTHER("其他文件"),
    FOLDER("文件目录"),
    TEXT("文本文件"),
    VIDEO("视频文件"),
    AUDIO("音频文件"),
    IMAGE("图像文件"),
    ;

    private final String description;
    FileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
