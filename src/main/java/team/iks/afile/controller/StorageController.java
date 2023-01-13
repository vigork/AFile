package team.iks.afile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import team.iks.afile.pojo.vo.DriverVO;
import team.iks.afile.driver.DriverRegistry;

import lombok.RequiredArgsConstructor;

/**
 * 存储库访问入口
 *
 * @author vigork
 * At: 2023/1/2
 */
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
@Tag(name = "存储库管理")
public class StorageController {
    private final DriverRegistry driverRegistry;

    @GetMapping("/drivers")
    @Operation(summary = "获取驱动器列表")
    public List<DriverVO> drivers() {
        return driverRegistry.drivers();
    }
}
