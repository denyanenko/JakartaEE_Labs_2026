<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="uk">

            <head>
                <meta charset="UTF-8">
                <title>Розклад та результати спортивних змагань</title>
                <style>
                    :root {
                        --primary: #2563eb;
                        --primary-hover: #1d4ed8;
                        --danger: #ef4444;
                        --success: #22c55e;
                        --bg: #f8fafc;
                        --card: #ffffff;
                        --border: #e2e8f0;
                        --text-main: #1e293b;
                        --text-muted: #64748b;
                    }

                    body {
                        background-color: var(--bg);
                        color: var(--text-main);
                        font-family: 'Inter', system-ui, -apple-system, sans-serif;
                        margin: 0;
                        padding: 40px;
                        line-height: 1.5;
                    }

                    .container {
                        max-width: 1000px;
                        margin: 0 auto;
                        background: var(--card);
                        padding: 32px;
                        border-radius: 16px;
                        box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
                    }

                    header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 32px;
                    }

                    h1 {
                        font-size: 1.875rem;
                        font-weight: 700;
                        color: var(--text-main);
                        margin: 0;
                    }

                    .role-badge {
                        padding: 6px 12px;
                        border-radius: 9999px;
                        font-size: 0.75rem;
                        font-weight: 600;
                        text-transform: uppercase;
                        background: var(--border);
                        color: var(--text-muted);
                    }

                    .role-admin {
                        background: #dbeafe;
                        color: var(--primary);
                    }

                    .controls {
                        display: flex;
                        gap: 16px;
                        margin-bottom: 24px;
                        flex-wrap: wrap;
                    }

                    .search-form {
                        display: flex;
                        gap: 8px;
                        flex-grow: 1;
                    }

                    input[type="text"] {
                        flex-grow: 1;
                        padding: 10px 16px;
                        border: 1px solid var(--border);
                        border-radius: 8px;
                        font-size: 0.95rem;
                    }

                    .btn {
                        padding: 10px 20px;
                        border-radius: 8px;
                        font-weight: 600;
                        cursor: pointer;
                        transition: all 0.2s;
                        border: none;
                        display: inline-flex;
                        align-items: center;
                        gap: 8px;
                        text-decoration: none;
                        font-size: 0.95rem;
                    }

                    .btn-primary {
                        background: var(--primary);
                        color: white;
                    }

                    .btn-primary:hover {
                        background: var(--primary-hover);
                    }

                    .btn-outline {
                        background: white;
                        border: 1px solid var(--border);
                        color: var(--text-main);
                    }

                    .btn-outline:hover {
                        background: var(--bg);
                    }

                    .btn-danger {
                        background: #fee2e2;
                        color: var(--danger);
                    }

                    .btn-danger:hover {
                        background: #fecaca;
                    }

                    table {
                        width: 100%;
                        border-collapse: separate;
                        border-spacing: 0;
                        margin-top: 8px;
                    }

                    th {
                        text-align: left;
                        padding: 16px;
                        background: #f1f5f9;
                        font-weight: 600;
                        font-size: 0.875rem;
                        color: var(--text-muted);
                        border-bottom: 2px solid var(--border);
                    }

                    td {
                        padding: 16px;
                        border-bottom: 1px solid var(--border);
                        vertical-align: middle;
                    }

                    tr:last-child td {
                        border-bottom: none;
                    }

                    .team-name {
                        font-weight: 600;
                        font-size: 1.05rem;
                    }

                    .vs {
                        color: var(--text-muted);
                        font-weight: 400;
                        margin: 0 8px;
                    }

                    .score-pill {
                        display: inline-block;
                        padding: 4px 12px;
                        background: var(--text-main);
                        color: white;
                        border-radius: 6px;
                        font-weight: 700;
                        font-family: monospace;
                        font-size: 1.1rem;
                    }

                    .status-tag {
                        font-size: 0.75rem;
                        font-weight: 600;
                        padding: 4px 8px;
                        border-radius: 4px;
                    }

                    .status-completed {
                        background: #dcfce7;
                        color: #166534;
                    }

                    .status-upcoming {
                        background: #fef9c3;
                        color: #854d0e;
                    }

                    .actions-cell {
                        display: flex;
                        gap: 8px;
                    }

                    .footer {
                        margin-top: 32px;
                        padding-top: 24px;
                        border-top: 1px solid var(--border);
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        font-size: 0.875rem;
                        color: var(--text-muted);
                    }

                    .btn-mini {
                        padding: 6px 12px;
                        font-size: 0.8rem;
                    }
                </style>
            </head>

            <body>

                <div class="container">
                    <header>
                        <h1>Спортивні змагання</h1>
                        <div class="role-badge ${isAdmin ? 'role-admin' : ''}">
                            ${isAdmin ? 'Адміністратор' : 'Гість'}
                        </div>
                    </header>

                    <div class="controls">
                        <form action="games" method="get" class="search-form">
                            <input type="hidden" name="role" value="<c:out value='${role}'/>">
                            <input type="text" name="search" placeholder="Пошук команди..."
                                value="<c:out value='${searchQuery}'/>">
                            <button type="submit" class="btn btn-primary">Пошук</button>
                            <c:if test="${not empty searchQuery}">
                                <a href="games?role=<c:out value='${role}'/>" class="btn btn-outline">Скинути</a>
                            </c:if>
                        </form>

                        <c:if test="${isAdmin}">
                            <a href="games?action=new&role=admin" class="btn btn-primary">+ Нова гра</a>
                        </c:if>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Дата та час</th>
                                <th>Учасники</th>
                                <th>Результат</th>
                                <th>Статус</th>
                                <c:if test="${isAdmin}">
                                    <th>Дії</th>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="game" items="${games}">
                                <tr>
                                    <td>
                                        <c:out value="${game.dateTime}" />
                                    </td>
                                    <td>
                                        <span class="team-name">
                                            <c:out value="${game.homeTeam.name}" />
                                        </span>
                                        <span class="vs">vs</span>
                                        <span class="team-name">
                                            <c:out value="${game.awayTeam.name}" />
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${game.result.completed}">
                                                <div class="score-pill">
                                                    <c:out value="${game.result.scoreDisplay}" />
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: var(--text-muted)">—</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${game.result.completed}">
                                                <span class="status-tag status-completed">Завершено</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-tag status-upcoming">Очікується</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <c:if test="${isAdmin}">
                                        <td class="actions-cell">
                                            <a href="games?action=edit&id=${game.id}&role=admin"
                                                class="btn btn-outline btn-mini">Редагувати</a>
                                            <form action="games" method="post" style="display:inline;"
                                                onsubmit="return confirm('Ви впевнені?')">
                                                <input type="hidden" name="id" value="${game.id}">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="role" value="admin">
                                                <button type="submit" class="btn btn-danger btn-mini">Видалити</button>
                                            </form>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <c:if test="${empty games}">
                        <div style="text-align: center; padding: 48px; color: var(--text-muted);">
                            Ігор не знайдено за вашим запитом.
                        </div>
                    </c:if>

                    <div class="footer">
                        <div>
                            Поточна роль: <strong>${isAdmin ? 'Адміністратор' : 'Гість'}</strong>
                        </div>
                        <c:choose>
                            <c:when test="${isAdmin}">
                                <a href="games" class="btn btn-outline btn-mini">Вийти (Гість)</a>
                            </c:when>
                            <c:otherwise>
                                <a href="games?role=admin" class="btn btn-outline btn-mini">Ввійти як Адмін</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

            </body>

            </html>