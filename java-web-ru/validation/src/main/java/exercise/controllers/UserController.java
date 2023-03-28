package exercise.controllers;

import io.javalin.http.Handler;
import java.util.List;
import java.util.Map;
import io.javalin.core.validation.Validator;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.JavalinValidation;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

import exercise.domain.User;
import exercise.domain.query.QUser;

public final class UserController {

    public static Handler listUsers = ctx -> {

        List<User> users = new QUser()
            .orderBy()
                .id.asc()
            .findList();

        ctx.attribute("users", users);
        ctx.render("users/index.html");
    };

    public static Handler showUser = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        User user = new QUser()
            .id.equalTo(id)
            .findOne();

        ctx.attribute("user", user);
        ctx.render("users/show.html");
    };

    public static Handler newUser = ctx -> {

        ctx.attribute("errors", Map.of());
        ctx.attribute("user", Map.of());
        ctx.render("users/new.html");
    };

    public static Handler createUser = ctx -> {
        // BEGIN
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Добавляем валидатор для каждого поля
        Validator<String> firstNameValidator = ctx.formParamAsClass("firstName", String.class)
                                                  // Добавляем проверку, что имя не должно быть пустым
                                                  .check(it -> !it.isEmpty(), "Поле ИМЯ не должно быть пустым");

        Validator<String> lastNameValidator = ctx.formParamAsClass("lastName", String.class)
                                                  // Добавляем проверку, что фамилия не должно быть пустым
                                                  .check(it -> !it.isEmpty(), "Поле ФАМИЛИЯ не должно быть пустым");

        Validator<String> emailValidator = ctx.formParamAsClass("email", String.class)
                                                      // Добавляем проверку, что фамилия не должно быть пустым
                                                      .check(it -> EmailValidator.getInstance().isValid(it), "Такого EMAIL не существует");

        // Можно добавить несколько проверок
        Validator<String> passwordValidator = ctx.formParamAsClass("password", String.class)
                                                     .check(it ->  StringUtils.isNumeric(it), "Количество работников должно содержать только цифры")
                                                     .check(it -> (it.length() >= 4), "Количество работников не должно быть пустым");

        // Валидируем данные из форм и собираем все ошибки валидации в словарь
        // Если ошибок нет, словарь будет пустой
        Map<String, List<ValidationError<? extends Object>>> errors = JavalinValidation.collectErrors(
                firstNameValidator,
                lastNameValidator,
                emailValidator,
                passwordValidator
                );

        // Если данные не валидные
        if (!errors.isEmpty()) {
            // Устанавливаем код ответа
            ctx.status(422);
            // Передаем ошибки и данные компании
            ctx.attribute("errors", errors);
            User user = new User(firstName, lastName, email, password);
            ctx.attribute("user", user);
            ctx.render("users/new.html");
            return;
        }

        User user = new User(firstName, lastName, email, password);
        user.save();

        ctx.sessionAttribute("flash", "Пользователь создан успешно");
        ctx.redirect("/users");
        // END
    };
}
