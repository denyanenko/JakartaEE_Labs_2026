package ua.edu.univ.schedule.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ua.edu.univ.schedule.dto.ErrorResponse;
import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.service.IGameService;

import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {
    @Inject
    private IGameService gameService;

    @GET
    public Response getGames(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("team") String teamName) {
        
        List<Game> games = gameService.getPagedGames(page, size, teamName);
        return Response.ok(games).build();
    }

    @GET
    @Path("/{id}")
    public Response getGame(@PathParam("id") Long id) {
        return gameService.getGameById(id)
                .map(game -> Response.ok(game).build())
                .orElseGet(() -> {
                    ErrorResponse error = new ErrorResponse(404, "Game not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
                });
    }

    @POST
    public Response createGame(@Valid Game game) {
        Game created = gameService.addGame(game);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateGame(@PathParam("id") Long id, @Valid Game game) {
        return gameService.updateGame(id, game)
                .map(updated -> Response.ok(updated).build())
                .orElseGet(() -> {
                    ErrorResponse error = new ErrorResponse(404, "Game not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
                });
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGame(@PathParam("id") Long id) {
        if (gameService.deleteGame(id)) {
            return Response.noContent().build();
        } else {
            ErrorResponse error = new ErrorResponse(404, "Game not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
