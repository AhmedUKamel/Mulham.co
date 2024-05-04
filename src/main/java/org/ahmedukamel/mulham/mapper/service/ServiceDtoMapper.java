package org.ahmedukamel.mulham.mapper.service;

import org.ahmedukamel.mulham.dto.service.ServiceDto;
import org.ahmedukamel.mulham.model.Service;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ServiceDtoMapper implements Function<Service, ServiceDto> {
    @Override
    public ServiceDto apply(Service service) {
        return new ServiceDto(
                service.getId(),
                service.getEnglishName(),
                service.getArabicName(),
                service.getEnglishDescription(),
                service.getArabicDescription(),
                service.getCost(),
                service.getCategory().getId(),
                service.getMinutes()
        );
    }
}
