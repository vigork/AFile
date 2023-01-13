package team.iks.afile.driver.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.Optional;

import team.iks.afile.exception.FileOperationException;

/**
 * TODO: description
 */
public abstract class LocalDriverUtils {
    public static String getSecureActualPath(String rootPath, String virtualAbsolutePath) {
        String normalizeVirtualAbsolutePath = Optional.ofNullable(virtualAbsolutePath)
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .map(p -> Paths.get(p).normalize().toString())
                .orElse(null);

        String actualPath = Paths.get(rootPath, normalizeVirtualAbsolutePath).toString();
        if (!actualPath.startsWith(rootPath)) {
            return rootPath;
        }
        return actualPath;
    }

    public static void rmr(File file) {
        if (file.isDirectory()) {
            for (File listFile : Optional.ofNullable(file.listFiles()).orElseGet(() -> new File[0])) {
                LocalDriverUtils.rmr(listFile);
            }
        }
        file.delete();
    }

    public static void cpr(File source, File target) {
        if (source.isDirectory()) {
            for (File listFile : Optional.ofNullable(source.listFiles()).orElseGet(() -> new File[0])) {
                File subTarget = new File(target, source.getName());
                subTarget.mkdirs();
                LocalDriverUtils.cpr(listFile, subTarget);
            }
        } else if (source.isFile()){
            target.getParentFile().mkdirs();
            try (FileInputStream in = new FileInputStream(source); FileOutputStream out = new FileOutputStream(target)) {
                FileChannel ic = in.getChannel();
                FileChannel oc = out.getChannel();
                ic.transferTo(0, ic.size(), oc);
            } catch (IOException e) {
                throw new FileOperationException("复制 File 失败");
            }
        }
    }
}
