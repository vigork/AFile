package team.iks.afile.driver.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;
import team.iks.afile.driver.AbstractDriver;
import team.iks.afile.driver.DriverInfo;
import team.iks.afile.enumerate.FileTypeEnum;
import team.iks.afile.exception.FileOperationException;
import team.iks.afile.pojo.vo.FileItemVO;

/**
 * 本地硬盘驱动器
 */
@DriverInfo(name = "本地存储")
@AutoService(AbstractDriver.class)
public class LocalDriver extends AbstractDriver<LocalDriverAttributes> {

    public LocalDriver(LocalDriverAttributes config) {
        super(config);
    }

    @Override
    public List<FileItemVO> list(String virtualAbsolutePath) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        if (!actualFile.isDirectory()) {
            throw new FileOperationException("该目录不存在");
        }

        return buildFileItems(actualFile.listFiles());
    }

    @Override
    public OutputStream read(String virtualAbsolutePath) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        if (!actualFile.isFile()) {
            throw new FileOperationException("该文件不存在");
        }

        try {
            return new FileOutputStream(actualFile);
        } catch (FileNotFoundException e) {
            throw new FileOperationException("读取文件流失败", e);
        }
    }

    @Override
    public FileItemVO makeDir(String virtualAbsolutePath) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        if (actualFile.isDirectory()) {
            throw new FileOperationException("该目录已存在");
        }

        // 当该路径下存在同名文件时将返回 false
        boolean mkdirs = actualFile.mkdirs();
        if (mkdirs && actualFile.isDirectory()) {
            return buildFileItems(Collections.singleton(actualFile).toArray(new File[1])).get(0);
        }

        throw new FileOperationException(String.format("创建目录失败, 请检查该 File 是否已存在: %s", virtualAbsolutePath));
    }

    @Override
    public FileItemVO write(String virtualAbsolutePath, InputStream in) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        actualFile.getParentFile().mkdirs();

        try (ReadableByteChannel src = Channels.newChannel(in); FileOutputStream out = new FileOutputStream(actualFile)) {
            out.getChannel().transferFrom(src, 0, in.available());
        } catch (IOException e) {
            throw new FileOperationException("写入文件出错", e);
        }
        if (!actualFile.isFile()) {
            throw new FileOperationException("写入文件失败");
        }

        return buildFileItems(Collections.singleton(actualFile).toArray(new File[1])).get(0);
    }

    @Override
    public void delete(String virtualAbsolutePath) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        if (!actualFile.exists()) {
            throw new FileOperationException("该 File 不存在");
        }
        LocalDriverUtils.rmr(actualFile);
    }

    @Override
    public FileItemVO move(String virtualAbsolutePath, String targetVirtualAbsolutePath) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        File targetActualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), targetVirtualAbsolutePath));
        if (!actualFile.exists()) {
            throw new FileOperationException("该 File 不存在");
        }

        boolean r = actualFile.renameTo(targetActualFile);
        if (r) {
            return buildFileItems(Collections.singleton(targetActualFile).toArray(new File[1])).get(0);
        }
        throw new FileOperationException("File 移动失败");
    }

    @Override
    public FileItemVO copy(String virtualAbsolutePath, String targetVirtualAbsolutePath) {
        File actualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), virtualAbsolutePath));
        File targetActualFile = new File(LocalDriverUtils.getSecureActualPath(config.getRootPath(), targetVirtualAbsolutePath));
        if (!actualFile.exists()) {
            throw new FileOperationException("该 File 不存在");
        }

        LocalDriverUtils.cpr(actualFile, targetActualFile);

        return buildFileItems(Collections.singleton(targetActualFile).toArray(new File[1])).get(0);
    }

    private List<FileItemVO> buildFileItems(File[] files) {
        return Arrays.stream(files)
                .map(file -> new FileItemVO()
                        .setName(file.getName())
                        .setPath(file.getAbsolutePath().substring(config.getRootPath().length()))
                        .setType(file.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.OTHER)
                        .setSize(file.isDirectory() ? null : file.length())
                        .setModifyTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()))
                ).collect(Collectors.toList());
    }
}
