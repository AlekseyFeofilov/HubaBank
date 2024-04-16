package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hubabank.core.entity.Property;
import ru.hubabank.core.entity.PropertyType;
import ru.hubabank.core.repository.PropertyRepository;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key, PropertyType.BOOLEAN).getValue());
    }

    public double getDouble(String key) {
        return Double.parseDouble(getProperty(key, PropertyType.DOUBLE).getValue());
    }

    public int getInt(String key) {
        return Integer.parseInt(getProperty(key, PropertyType.INT).getValue());
    }

    private Property getProperty(String key, PropertyType type) {
        Property property = propertyRepository.findById(key)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Свойство %s не найдено", key)));
        if (property.getType() != type) {
            throw new IllegalArgumentException(
                    String.format("Свойство %s не является %s", key, type.name().toLowerCase()));
        }
        return property;
    }
}
