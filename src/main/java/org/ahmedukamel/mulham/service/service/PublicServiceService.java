package org.ahmedukamel.mulham.service.service;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.service.ServiceResponse;
import org.ahmedukamel.mulham.mapper.service.ServiceResponseMapper;
import org.ahmedukamel.mulham.model.Service;
import org.ahmedukamel.mulham.repository.CategoryRepository;
import org.ahmedukamel.mulham.repository.ServiceRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;

import java.util.Comparator;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PublicServiceService implements IPublicServiceService {
    final ServiceRepository serviceRepository;
    final CategoryRepository categoryRepository;
    final ServiceResponseMapper mapper;

    @Override
    public Object getService(Integer id) {
        Service service = DatabaseFetcher.get(serviceRepository::findById, id, Service.class);
        ServiceResponse response = mapper.apply(service);

        return new ApiResponse(true, "Service returned successfully!", response);
    }

    @Override
    public Object getServices() {
        List<ServiceResponse> response = serviceRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Service::getId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "All Services returned successfully!", response);
    }

    @Override
    public Object getServices(long pageSize, long pageNumber) {
        List<ServiceResponse> response = serviceRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Service::getId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "All Services returned successfully!", response);
    }
}