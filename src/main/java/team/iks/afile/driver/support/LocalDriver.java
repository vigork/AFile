package team.iks.afile.driver.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import team.iks.afile.driver.annotation.DriverAttributeInfo;
import team.iks.afile.driver.AbstractDriver;
import team.iks.afile.driver.DriverAttributes;
import team.iks.afile.driver.FileItem;
import team.iks.afile.driver.annotation.DriverInfo;
import team.iks.afile.exception.FileOperationException;
import team.iks.afile.util.FileOperationUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 本地存储库驱动
 *
 * @author vigork
 * At: 2023/1/1
 */
@DriverInfo(name = "本地存储")
public class LocalDriver extends AbstractDriver<LocalDriver.Attributes> {
    @Data
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    public static class Attributes extends DriverAttributes {
        @DriverAttributeInfo(label = "物理路径", required = true)
        private String rootPath;
    }

    public LocalDriver(Attributes config) {
        super(config);
    }

    @Override
    public List<FileItem> list(String virtualPath) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));
        if (!actualFile.exists()) {
            throw new FileOperationException("文件不存在");
        }

        return buildFileItems(actualFile.listFiles());
    }

    @Override
    public OutputStream read(String virtualPath) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));
        try {
            return new FileOutputStream(actualFile);
        } catch (FileNotFoundException e) {
            throw new FileOperationException("读取文件失败", e);
        }
    }

    @Override
    public FileItem makeDir(String virtualPath) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));

        String exp = "文件夹已存在";
        if (!actualFile.exists()) {
            boolean result = actualFile.mkdirs();
            if (result) {
                exp = null;
            } else {
                exp = "创建文件夹失败";
            }
        }

        if (null != exp) {
            throw new FileOperationException(exp);
        }

        return buildFileItem(actualFile);
    }

    @Override
    public FileItem write(String virtualPath, InputStream in) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));
        try (ReadableByteChannel src = Channels.newChannel(in); FileOutputStream out = new FileOutputStream(actualFile)) {
            out.getChannel().transferFrom(src, 0, in.available());
        } catch (IOException e) {
            throw new FileOperationException("文件写入失败");
        }

        return buildFileItem(actualFile);
    }

    @Override
    public void delete(String virtualPath) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));
        if (!actualFile.delete()) {
            throw new FileOperationException("文件删除失败");
        }
    }

    @Override
    public FileItem move(String virtualPath, String newVirtualPath) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));
        File actualNewFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(newVirtualPath));
        if (!actualFile.renameTo(actualNewFile)) {
            throw new FileOperationException("移动文件失败");
        }

        return buildFileItem(actualNewFile);
    }

    @Override
    public FileItem copy(String virtualPath, String targetVirtualPath) {
        File actualFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(virtualPath));
        File actualNewFile = new File(config.getRootPath(), FileOperationUtils.asAbsolutePath(targetVirtualPath));

        try (FileInputStream is = new FileInputStream(actualFile); FileOutputStream os = new FileOutputStream(actualNewFile)) {
            FileChannel ic = is.getChannel();
            FileChannel oc = os.getChannel();
            ic.transferTo(0, ic.size(), oc);
        } catch (IOException e) {
            throw new FileOperationException("复制文件失败");
        }

        return buildFileItem(actualNewFile);
    }

    private List<FileItem> buildFileItems(File[] files) {
        return Arrays.stream(files)
                .map(this::buildFileItem)
                .collect(Collectors.toList());
    }

    private FileItem buildFileItem(File file) {
        return new FileItem()
                .setName(file.getName())
                .setPath(file.getAbsolutePath().substring(config.getRootPath().length()))
                .setType(FileOperationUtils.getFileType(file))
                .setSize(file.isDirectory() ? null : file.length())
                .setModifyTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()));
    }
}
