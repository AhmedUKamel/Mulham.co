package org.ahmedukamel.mulham.mapper.service;

import org.ahmedukamel.mulham.dto.service.ServiceResponse;
import org.ahmedukamel.mulham.model.Service;
import org.ahmedukamel.mulham.util.ContextHolderUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ServiceResponseMapper implements Function<Service, ServiceResponse> {
    @Override
    public ServiceResponse apply(Service service) {
        String name = ContextHolderUtils.getLanguageCode()
                .equalsIgnoreCase("en") ? service.getEnglishName() :
                service.getArabicName();

        String description = ContextHolderUtils.getLanguageCode()
                .equalsIgnoreCase("en") ? service.getEnglishDescription() :
                service.getArabicDescription();

        String categoryName = ContextHolderUtils.getLanguageCode()
                .equalsIgnoreCase("en") ? service.getCategory().getEnglishName() :
                service.getCategory().getArabicName();


        ServiceResponse response = new ServiceResponse();
        response.setId(service.getId());
        response.setName(name);
        response.setDescription(description);
        response.setCost(service.getCost());
        response.setCategoryId(service.getCategory().getId());
        response.setCategoryName(categoryName);
        return response;
    }
}