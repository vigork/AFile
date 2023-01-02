package team.iks.afile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.iks.afile.driver.DriverInfo;
import team.iks.afile.driver.DriverContext;

/**
 * 存储库访问入口
 *
 * @author vigork
 * At: 2023/1/2
 */
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {
    private final DriverContext driverContext;

    @GetMapping("/drivers")
    public List<DriverInfo> drivers() {
        return driverContext.drivers();
    }
}
