package br.org.mac.famec.rest;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {
    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {

        response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHttpHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        response.getHttpHeaders().add("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, DELETE, OPTIONS");

        return response;
    }
}