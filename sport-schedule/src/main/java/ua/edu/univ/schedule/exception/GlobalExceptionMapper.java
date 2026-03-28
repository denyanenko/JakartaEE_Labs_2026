package ua.edu.univ.schedule.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ua.edu.univ.schedule.dto.ErrorResponse;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();


        if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;
            ErrorResponse error = new ErrorResponse(webEx.getResponse().getStatus(), webEx.getMessage());
            return Response.status(webEx.getResponse().getStatus())
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        ErrorResponse defaultError = new ErrorResponse(500, "Internal server error. We are already working on it.");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(defaultError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}