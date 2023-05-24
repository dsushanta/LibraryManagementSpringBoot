package com.bravo.johny.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CommonUtils {

    public static Logger getLoggerInstance(Class loggingClass) {
        return LoggerFactory.getLogger(loggingClass);
    }

    public static URI getURISelf(@Context UriInfo uriInfo, String id, Object object) {

        return uriInfo.getBaseUriBuilder()
                .path(object.getClass())
                .path(id)
                .build();
    }

    public static File getResourceAsFileObject(Object object, String resourceFilename) {
        File file = new File(
                object.getClass()
                        .getClassLoader()
                        .getResource(resourceFilename)
                        .getFile()
        );

        return file;
    }

    public static String getResourceFullPath(Object object, String resourceFilename) {
        return object.getClass()
                .getClassLoader()
                .getResource(resourceFilename)
                .getFile();
    }

    public static void throwBadRequestRuntimeException(String message) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                message
        );
    }

    public static void returnErrorInResponse(HttpServletResponse response, Exception exception) throws IOException {

        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        var error = new HashMap<String, String>();
        error.put("error_message", exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
