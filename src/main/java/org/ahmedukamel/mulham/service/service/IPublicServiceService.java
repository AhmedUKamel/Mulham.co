package org.ahmedukamel.mulham.service.service;

public interface IPublicServiceService {

    Object getService(Integer id);

    Object getServices();

    Object getServices(long pageSize, long pageNumber);
}