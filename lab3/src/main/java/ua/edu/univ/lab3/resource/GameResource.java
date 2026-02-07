package ua.edu.univ.lab3.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ua.edu.univ.lab3.model.Game;
import ua.edu.univ.lab3.service.GameService;

import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

    private final GameService gameService = new GameService();

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
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
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
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGame(@PathParam("id") Long id) {
        if (gameService.deleteGame(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
