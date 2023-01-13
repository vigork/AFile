package team.iks.afile.driver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import team.iks.afile.pojo.vo.DriverAttributeVO;
import team.iks.afile.pojo.vo.DriverVO;

import lombok.RequiredArgsConstructor;

/**
 * 驱动加载器
 *
 * @author vigork
 * At: 2023/1/2
 */
@Component
@RequiredArgsConstructor
public class DriverRegistry {
    private final List<Class<? extends AbstractDriver<?>>> drivers = new ArrayList<>();


    /**
     * 获取已加载的存储库驱动信息
     */
    public List<DriverVO> drivers() {
        return drivers.stream().map(driver -> {
            DriverInfo driverInfoAnnotation = driver.getAnnotation(DriverInfo.class);

            return new DriverVO()
                    .setName(Optional.ofNullable(driverInfoAnnotation).map(DriverInfo::name).orElse(driver.getSimpleName()))
                    .setDriver(driver)
                    .setAttributes(buildDriverAttributes(driver))
                    .setOrder(Optional.ofNullable(driverInfoAnnotation).map(DriverInfo::order).orElse(Integer.MAX_VALUE));
        }).collect(Collectors.toList());
    }

    private List<DriverAttributeVO> buildDriverAttributes(Class<? extends AbstractDriver<?>> driver) {
        Class<?> actualDriverAttributes = (Class<?>) ((ParameterizedTypeImpl) driver.getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] attributesDeclaredFields = actualDriverAttributes.getDeclaredFields();

        List<DriverAttributeVO> result = new ArrayList<>();
        for (Field attributesDeclaredField : attributesDeclaredFields) {
            DriverAttributeInfo driverAttributeInfoAnnotation = attributesDeclaredField.getAnnotation(DriverAttributeInfo.class);
            if (null == driverAttributeInfoAnnotation) {
                continue;
            }

            DriverAttributeVO driverAttributeVOInfo = new DriverAttributeVO()
                    .setLabel(driverAttributeInfoAnnotation.label())
                    .setName(attributesDeclaredField.getName())
                    .setDescription(driverAttributeInfoAnnotation.description())
                    .setRequired(driverAttributeInfoAnnotation.required())
                    .setOrder(driverAttributeInfoAnnotation.order());
            result.add(driverAttributeVOInfo);
        }

        return result;
    }
}
