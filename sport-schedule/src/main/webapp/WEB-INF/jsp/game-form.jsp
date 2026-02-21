<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="uk">

        <head>
            <meta charset="UTF-8">
            <title>${game == null ? 'Додати гру' : 'Редагувати гру'}</title>
            <style>
                :root {
                    --primary: #2563eb;
                    --secondary: #64748b;
                    --bg: #f8fafc;
                    --card: #ffffff;
                    --text: #1e293b;
                }

                body {
                    font-family: 'Inter', system-ui, -apple-system, sans-serif;
                    background-color: var(--bg);
                    color: var(--text);
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    min-height: 100vh;
                    margin: 0;
                }

                .form-card {
                    background: var(--card);
                    padding: 32px;
                    border-radius: 16px;
                    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    max-width: 500px;
                }

                h1 {
                    margin-top: 0;
                    font-size: 1.5rem;
                    color: var(--primary);
                }

                .form-group {
                    margin-bottom: 20px;
                }

                label {
                    display: block;
                    margin-bottom: 6px;
                    font-weight: 500;
                    font-size: 0.9rem;
                }

                input[type="text"],
                input[type="datetime-local"],
                input[type="number"] {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #e2e8f0;
                    border-radius: 8px;
                    box-sizing: border-box;
                    transition: border-color 0.2s;
                }

                input:focus {
                    outline: none;
                    border-color: var(--primary);
                }

                .row {
                    display: flex;
                    gap: 16px;
                }

                .row>.form-group {
                    flex: 1;
                }

                .checkbox-group {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                }

                .actions {
                    display: flex;
                    gap: 12px;
                    margin-top: 24px;
                }

                button {
                    flex: 1;
                    padding: 12px;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                    font-weight: 600;
                    transition: opacity 0.2s;
                }

                .btn-save {
                    background: var(--primary);
                    color: white;
                }

                .btn-cancel {
                    background: #e2e8f0;
                    color: var(--secondary);
                    text-decoration: none;
                    text-align: center;
                    line-height: 1.5;
                }
            </style>
        </head>

        <body>
            <div class="form-card">
                <h1>${game == null ? 'Створення нової гри' : 'Редагування гри'}</h1>
                <form action="games" method="post">
                    <input type="hidden" name="role" value="admin">
                    <input type="hidden" name="id" value="${game.id}">

                    <div class="form-group">
                        <label>Команда 1 (Господарі)</label>
                        <input type="text" name="homeTeam" value="<c:out value='${game.homeTeam.name}'/>" required>
                    </div>

                    <div class="form-group">
                        <label>Команда 2 (Гості)</label>
                        <input type="text" name="awayTeam" value="<c:out value='${game.awayTeam.name}'/>" required>
                    </div>

                    <div class="form-group">
                        <label>Дата та час</label>
                        <input type="datetime-local" name="dateTime" value="${game.dateTime}" required>
                    </div>

                    <div class="row">
                        <div class="form-group">
                            <label>Рахунок (Господарі)</label>
                            <input type="number" name="homeScore" min="0"
                                value="${game.result.homeScore != null ? game.result.homeScore : 0}">
                        </div>
                        <div class="form-group">
                            <label>Рахунок (Гості)</label>
                            <input type="number" name="awayScore" min="0"
                                value="${game.result.awayScore != null ? game.result.awayScore : 0}">
                        </div>
                    </div>

                    <div class="form-group checkbox-group">
                        <input type="checkbox" name="completed" id="completed" ${game.result.completed ? 'checked' : ''
                            }>
                        <label for="completed">Гра завершена</label>
                    </div>

                    <div class="actions">
                        <button type="submit" class="btn-save">Зберегти</button>
                        <a href="games?role=admin" class="btn-cancel">Скасувати</a>
                    </div>
                </form>
            </div>
        </body>

        </html>