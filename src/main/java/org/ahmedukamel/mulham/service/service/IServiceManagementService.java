package org.ahmedukamel.mulham.service.service;

public interface IServiceManagementService {
    Object addService(Object object);

    Object getService(Integer id);

    Object getServices();

    Object getServices(long pageSize, long pageNumber);

    Object updateService(Integer id, Object object);

    void deleteService(Integer id);
}