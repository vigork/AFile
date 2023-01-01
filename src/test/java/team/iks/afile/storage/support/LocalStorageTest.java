package team.iks.afile.storage.support;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import team.iks.afile.storage.FileItem;

/**
 * TODO: description
 *
 * @author vigork
 * At: 2023/1/1
 */
class LocalStorageTest {
    private static LocalStorage localStorage;

    @BeforeAll
    static void init() {
        localStorage = new LocalStorage(new LocalStorage.Config().setRootPath("/tmp"));
    }

    @Test
    void list() {
        List<FileItem> list = localStorage.list("");
        System.out.println(list);
    }
}