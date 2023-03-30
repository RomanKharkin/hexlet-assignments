package exercise.controllers;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.DB;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import java.util.List;

public class UserController implements CrudHandler {

    public void getAll(Context ctx) {
        // BEGIN
        List<User> users = new QUser()
                                   .orderBy()
                                   .id.asc()
                                   .findList();

        String json = DB.json().toJson(users);
        ctx.json(json);
        // END
    }

    ;

    public void getOne(Context ctx, String id) {

        // BEGIN
        User user = new QUser()
                            .id.equalTo(Integer.parseInt(id))
                            .findOne();

        String json = DB.json().toJson(user);
        ctx.json(json);
        // END
    }

    ;

    public void create(Context ctx) {

        // BEGIN
        var userToCreate = ctx.bodyAsClass(User.class);
        User user = new User(userToCreate.getFirstName(), userToCreate.getLastName(), userToCreate.getEmail(), userToCreate.getPassword());
        user.save();
        ctx.json(user.getId());
        // END
    }

    ;

    public void update(Context ctx, String id) {
        // BEGIN
        System.out.println(id);
        var updatedUser = ctx.bodyAsClass(User.class);
        new QUser()
                .id.equalTo(Long.parseLong(id))
                .asUpdate()
                .set("firstName", updatedUser.getFirstName())
                .set("lastName", updatedUser.getLastName())
                .set("email", updatedUser.getEmail())
                .set("password", updatedUser.getPassword())
                .update();
        // END
    }

    ;

    public void delete(Context ctx, String id) {
        // BEGIN
        new QUser().id.equalTo(Long.parseLong(id)).delete();
        // END
    }

    ;
}
