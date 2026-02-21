package ua.edu.univ.lab1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");

        String fullName = "Невідомий учасник";
        String linkedin = "#";
        String photo = "";

        String path = request.getContextPath();

        if ("shylo".equals(id)) {
            fullName = "Шило Михайло";
            linkedin = "https://www.linkedin.com/in/mykhailo-shylo-b0911221b/";
            photo = "Shylo.png";
        } else if ("bondarchuk".equals(id)) {
            fullName = "Бондарчук Олександр";
            linkedin = "https://www.linkedin.com/in/oleksandr-bondarchuk-71152527b/";
            photo = "Bondarchuk.png";
        } else if ("yanenko".equals(id)) {
            fullName = "Яненко Денис";
            linkedin = "https://www.linkedin.com/in/denys-yanenko/";
            photo = "Yanenko.png";
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Профіль " + fullName + "</title></head><body>");

            out.println("<h1>Учасник: " + fullName + "</h1>");

            // Додаємо відображення фото, якщо воно знайдене
            if (!photo.isEmpty()) {
                out.println("<img src='images/" + photo + "' alt='" + fullName + "' width='250' style='border-radius: 10px;'><br>");
            }

            out.println("<p>LinkedIn: <a href='" + linkedin + "' target='_blank'>Профіль користувача</a></p>");

            out.println("<hr>");
            out.println("<a href='" + path + "/'>Повернутися на головну</a>");
            out.println("</body></html>");
        }
    }
}