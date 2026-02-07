<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="uk">

            <head>
                <meta charset="UTF-8">
                <title>Розклад та результати спортивних змагань</title>
                <style>
                    body {
                        background-color: #f4f7f6;
                        color: #333;
                        margin: 40px;
                    }

                    h1 {
                        color: #2c3e50;
                    }

                    .container {
                        background: white;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 20px;
                    }

                    th,
                    td {
                        padding: 12px;
                        border: 1px solid #ddd;
                        text-align: left;
                    }

                    th {
                        background-color: #3498db;
                        color: white;
                    }

                    tr:nth-child(even) {
                        background-color: #f9f9f9;
                    }

                    .status-completed {
                        color: #27ae60;
                        font-weight: bold;
                    }

                    .status-upcoming {
                        color: #e67e22;
                        font-weight: bold;
                    }

                    .search-box {
                        margin-bottom: 20px;
                    }

                    input[type="text"] {
                        padding: 8px;
                        width: 250px;
                        border-radius: 4px;
                        border: 1px solid #ccc;
                    }

                    button {
                        padding: 8px 15px;
                        background: #3498db;
                        color: white;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                    }

                    .admin-panel {
                        background: #fee;
                        border: 1px solid #fcc;
                        padding: 10px;
                        margin-bottom: 20px;
                        border-radius: 4px;
                    }
                </style>
            </head>

            <body>

                <div class="container">
                    <h1>Спортивні змагання</h1>

                    <c:if test="${isAdmin}">
                        <div class="admin-panel">
                            <strong>Панель адміністратора:</strong> Ви можете редагувати розклад.
                            <button>Додати гру</button>
                        </div>
                    </c:if>

                    <div class="search-box">
                        <form action="games" method="get">
                            <input type="text" name="search" placeholder="Пошук команди..."
                                value="<c:out value='${searchQuery}'/>">
                            <button type="submit">Шукати</button>
                            <a href="games">Скинути</a>
                        </form>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Дата та час</th>
                                <th>Команда 1 (Дім)</th>
                                <th>Команда 2 (Виїзд)</th>
                                <th>Результат</th>
                                <th>Статус</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="game" items="${games}">
                                <tr>
                                    <td>
                                        <c:out value="${game.dateTime}" />
                                    </td>
                                    <td><strong>
                                            <c:out value="${game.homeTeam}" />
                                        </strong></td>
                                    <td><strong>
                                            <c:out value="${game.awayTeam}" />
                                        </strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${game.completed}">
                                                <c:out value="${game.score}" />
                                            </c:when>
                                            <c:otherwise>
                                                <em>Очікується</em>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${game.completed}">
                                            <span class="status-completed">Завершено</span>
                                        </c:if>
                                        <c:if test="${!game.completed}">
                                            <span class="status-upcoming">Майбутня гра</span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <c:if test="${empty games}">
                        <p>Ігор не знайдено за вашим запитом.</p>
                    </c:if>

                    <p style="margin-top: 20px;">
                        <small>Поточна роль:
                            <c:choose>
                                <c:when test="${isAdmin}">Адміністратор</c:when>
                                <c:otherwise>Гість (додайте <code>?role=admin</code> до URL для входу)</c:otherwise>
                            </c:choose>
                        </small>
                    </p>
                </div>

            </body>

            </html>