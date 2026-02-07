package ua.edu.univ.lab2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.univ.lab2.model.Game;
import ua.edu.univ.lab2.service.GameService;

import java.io.IOException;
import java.util.List;

@WebServlet("/games")
public class GameServlet extends HttpServlet {
    private GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
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
        
        String role = request.getParameter("role");
        if ("admin".equals(role)) {
            request.setAttribute("isAdmin", true);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/game-list.jsp").forward(request, response);
    }
}
