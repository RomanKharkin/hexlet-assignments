package exercise;

// Импортируем зависимости, необходимые для работы приложения
import io.javalin.Javalin;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

import org.thymeleaf.TemplateEngine;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import exercise.controllers.RootController;
import exercise.controllers.ArticleController;

public final class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "5000");
        return Integer.valueOf(port);
    }

    // Javalin поддерживает работу с шаблонизатором thymeleaf
    private static TemplateEngine getTemplateEngine() {
        // Создаём инстанс движка шаблонизатора
        TemplateEngine templateEngine = new TemplateEngine();
        // Добавляем к нему диалекты
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        // Настраиваем преобразователь шаблонов, так, чтобы обрабатывались
        // шаблоны в директории /templates/
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        // Добавляем преобразователь шаблонов к движку шаблонизатора
        templateEngine.addTemplateResolver(templateResolver);

        return templateEngine;
    }

    // Метод добавляет маршруты в переданное приложение
    private static void addRoutes(Javalin app) {
        // Для GET-запроса на маршрут / будет выполняться
        // обработчик welcome в контроллере RootController
        app.get("/", RootController.welcome);

        // При помощи методов routes() и path() маршруты можно группировать

        // BEGIN
        // Добавляем маршруты в приложение. Имя метода соответствует глаголу HTTP
        // Метод get добавляет обработчик, который будет выполняться для GET запроса по указанному пути

        // Добавляем обработчик для POST запроса по пути */companies*

        // При помощи методов routes() и path() маршруты можно группировать по пути
        //  * *GET /articles* — список всех статей
        //  * *POST /articles* — создание новой статьи
        //  * *GET /articles/new* — вывод формы создания новой статьи

        app.routes(() -> {
            path("articles", () -> {
                // GET /articles
                get(ArticleController.listArticles);
                // POST /articles
                post(ArticleController.createArticle);
                // GET /articles/new
                get("new", ArticleController.newArticle);
            });
        });

        //  * *GET /articles/{id}* — просмотр конкретной статьи
        // * *GET /articles/{id}/edit* — вывод формы редактирования статьи
        // * *POST /articles/{id}/edit* — обновление данных статьи
        // * *GET /articles/{id}/delete* — вывод страницы с подтверждением удаления статьи
        // * *POST /articles/{id}/delete* — удаление статьи

        // Создание динамического маршрута, в котором часть пути переменная GET /articles/101

        app.get("articles/{id}", ArticleController.showArticle);
        app.get("articles/{id}/edit", ArticleController.editArticle);
        app.post("articles/{id}/edit", ArticleController.updateArticle);
        app.get("articles/{id}/delete", ArticleController.deleteArticle);
        app.post("articles/{id}/delete", ArticleController.destroyArticle);

        // В обработчике можно будет получить переменную часть пути при помощи метода ctx.pathParam("id")
        // END
    }

    public static Javalin getApp() {

        // Создаём приложение
        Javalin app = Javalin.create(config -> {
            // Включаем логгирование
            config.enableDevLogging();
            // Подключаем настроенный шаблонизатор к фреймворку
            JavalinThymeleaf.configure(getTemplateEngine());
        });

        // Добавляем маршруты в приложение
        addRoutes(app);

        // Обработчик before запускается перед каждым запросом
        // Устанавливаем атрибут ctx для запросов
        app.before(ctx -> {
            ctx.attribute("ctx", ctx);
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }
}
