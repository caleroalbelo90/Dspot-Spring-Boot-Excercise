package com.dspot.profile.main.util;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class ObjectUpdater {

    public static void updateFields(Object target, Object source) {
        PropertyDescriptor[] sourceDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        PropertyDescriptor[] targetDescriptors = BeanUtils.getPropertyDescriptors(target.getClass());

        for (PropertyDescriptor sourceDescriptor : sourceDescriptors) {
            String propertyName = sourceDescriptor.getName();
            PropertyDescriptor targetDescriptor = findPropertyDescriptor(targetDescriptors, propertyName);

            if (targetDescriptor != null) {
                Method sourceReadMethod = sourceDescriptor.getReadMethod();
                Method targetWriteMethod = targetDescriptor.getWriteMethod();

                if (sourceReadMethod != null && targetWriteMethod != null) {
                    try {
                        Object sourceValue = sourceReadMethod.invoke(source);
                        Object targetValue = targetDescriptor.getReadMethod().invoke(target);

                        if (sourceValue != null && !sourceValue.equals(targetValue) && !sourceValue.toString().trim().isEmpty()) {
                            targetWriteMethod.invoke(target, sourceValue);
                        }
                    } catch (Exception e) {
                        // Handle exception if needed
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static PropertyDescriptor findPropertyDescriptor(PropertyDescriptor[] descriptors, String propertyName) {
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getName().equals(propertyName)) {
                return descriptor;
            }
        }
        return null;
    }

}
