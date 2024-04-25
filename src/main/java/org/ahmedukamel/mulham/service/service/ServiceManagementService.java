package org.ahmedukamel.mulham.service.service;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.service.ServiceDto;
import org.ahmedukamel.mulham.mapper.service.ServiceDtoMapper;
import org.ahmedukamel.mulham.model.Category;
import org.ahmedukamel.mulham.model.Service;
import org.ahmedukamel.mulham.repository.CategoryRepository;
import org.ahmedukamel.mulham.repository.ServiceRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceManagementService implements IServiceManagementService {
    final ServiceRepository serviceRepository;
    final CategoryRepository categoryRepository;
    final ServiceDtoMapper mapper;

    @Override
    public Object addService(Object object) {
        ServiceDto request = (ServiceDto) object;
        Category category = DatabaseFetcher.get(categoryRepository::findById, request.categoryId(), Category.class);

        Service service = new Service();
        BeanUtils.copyProperties(request, service);
        service.setCategory(category);

        Service savedService = serviceRepository.save(service);
        ServiceDto response = mapper.apply(savedService);

        return new ApiResponse(true, "Service added successfully!", response);
    }

    @Override
    public Object getService(Integer id) {
        Service service = DatabaseFetcher.get(serviceRepository::findById, id, Service.class);
        ServiceDto response = mapper.apply(service);

        return new ApiResponse(true, "Service returned successfully!", response);
    }

    @Override
    public Object getServices() {
        List<ServiceDto> response = serviceRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Service::getId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "All Services returned successfully!", response);
    }

    @Override
    public Object getServices(long pageSize, long pageNumber) {
        List<ServiceDto> response = serviceRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Service::getId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "All Services returned successfully!", response);
    }

    @Override
    public Object updateService(Integer id, Object object) {
        ServiceDto request = (ServiceDto) object;
        Service service = DatabaseFetcher.get(serviceRepository::findById, id, Service.class);
        Category category = DatabaseFetcher.get(categoryRepository::findById, request.categoryId(), Category.class);

        BeanUtils.copyProperties(request, service);
        service.setCategory(category);
        service.setId(id);

        Service savedService = serviceRepository.save(service);
        ServiceDto response = mapper.apply(savedService);

        return new ApiResponse(true, "Service updated successfully!", response);
    }

    @Override
    public void deleteService(Integer id) {
        Service service = DatabaseFetcher.get(serviceRepository::findById, id, Service.class);
        serviceRepository.delete(service);
    }
}