package team.iks.afile.driver.support;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import team.iks.afile.driver.FileItem;

/**
 * TODO: description
 *
 * @author vigork
 * At: 2023/1/1
 */
class LocalStorageTest {
    private static LocalDriver localStorage;

    @BeforeAll
    static void init() {
        localStorage = new LocalDriver(new LocalDriver.Config().setRootPath("/tmp"));
    }

    @Test
    void list() {
        List<FileItem> list = localStorage.list("");
        System.out.println(list);
    }
}