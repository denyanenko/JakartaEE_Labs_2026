package ua.edu.univ.schedule.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.model.GameResult;
import ua.edu.univ.schedule.model.Team;
import ua.edu.univ.schedule.service.GameService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/games")
public class GameServlet extends HttpServlet {
    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        String role = request.getParameter("role");
        boolean isAdmin = "admin".equals(role);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("role", role);

        switch (action) {
            case "new":
                if (!isAdmin) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                showEditForm(request, response, null);
                break;
            case "edit":
                if (!isAdmin) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                showEditForm(request, response, Long.parseLong(request.getParameter("id")));
                break;
            default:
                listGames(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String role = request.getParameter("role");
        if (!"admin".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            long id = Long.parseLong(request.getParameter("id"));
            gameService.deleteGame(id);
        } else {
            saveGame(request);
        }
        
        response.sendRedirect("games?role=admin");
    }

    private void listGames(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        List<Game> games;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            games = gameService.searchGamesByTeam(searchQuery);
            request.setAttribute("searchQuery", searchQuery);
        } else {
            games = gameService.getAllGames();
        }
        request.setAttribute("games", games);
        request.getRequestDispatcher("/WEB-INF/jsp/game-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {
        if (id != null) {
            gameService.getGameById(id).ifPresent(game -> request.setAttribute("game", game));
        }
        request.getRequestDispatcher("/WEB-INF/jsp/game-form.jsp").forward(request, response);
    }

    private void saveGame(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String homeTeamName = request.getParameter("homeTeam");
        String awayTeamName = request.getParameter("awayTeam");
        String dateTimeStr = request.getParameter("dateTime");
        int homeScore = Integer.parseInt(request.getParameter("homeScore"));
        int awayScore = Integer.parseInt(request.getParameter("awayScore"));
        boolean completed = request.getParameter("completed") != null;

        Game game = new Game();
        game.setHomeTeam(new Team(null, homeTeamName));
        game.setAwayTeam(new Team(null, awayTeamName));
        game.setDateTime(LocalDateTime.parse(dateTimeStr));
        game.setResult(new GameResult(homeScore, awayScore, completed));

        if (idStr == null || idStr.isEmpty()) {
            gameService.addGame(game);
        } else {
            gameService.updateGame(Long.parseLong(idStr), game);
        }
    }
}
