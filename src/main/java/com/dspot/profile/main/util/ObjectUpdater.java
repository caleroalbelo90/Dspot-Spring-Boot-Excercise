package com.dspot.profile.main.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

public class ObjectUpdater {
    public static void updateFields(Object target, Object source) {
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);

        for (PropertyDescriptor propertyDescriptor : targetWrapper.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            Object targetValue = targetWrapper.getPropertyValue(propertyName);
            Object sourceValue = sourceWrapper.getPropertyValue(propertyName);

            if (sourceValue != null && !sourceValue.equals(targetValue)) {
                targetWrapper.setPropertyValue(propertyName, sourceValue);
            }
        }
    }
}
