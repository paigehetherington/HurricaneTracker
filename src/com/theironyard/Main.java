package com.theironyard;

//FROM ZACH OAKES SPARK LECTURE

import spark.ModelAndView;
import spark.Session;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here

        HashMap<String, User> users = new HashMap<>();
        ArrayList<Hurricane> hurricanes = new ArrayList<>();

        Spark.get(
                "/",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    HashMap m = new HashMap();
                    if (user != null) {
                        m.put("name", user.name);
                    }
                    for (Hurricane h : hurricanes) {
                        h.isMe = user != null && h.user.name.equals(user.name);
                    }
                    m.put("hurricanes", hurricanes);
                    return new ModelAndView(m, "home.html");

                }
        );


        Spark.post(
                "/login",
                (request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("password");

                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name, password);
                        users.put(name, user);
                    }
                    else if (!password.equals(user.password)) {
                        response.redirect("/");
                        return null;
                    }

                    Session session = request.session();
                    session.attribute("loginName", name);
                    response.redirect("/");
                    return null;
                        }
                );

        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return null;
                }
        );

        Spark.post(
                "/hurricane",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    if (user == null) {
                        return null;
                    }

                    String hname = request.queryParams("hname");
                    String hlocation = request.queryParams("hlocation");
                    Hurricane.Category hcategory = Enum.valueOf(Hurricane.Category.class, request.queryParams("hcategory"));
                    String himage = request.queryParams("himage");
                    Hurricane h = new Hurricane(hurricanes.size(), hname, hlocation, hcategory, himage, user, false);
                    hurricanes.add(h);

                    response.redirect("/");
                    return null;
                }
        );

        Spark.post(
                "/delete-hurricane",
                (request, response) ->  {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    if (user == null) {
                        return null;
                    }

                    int id = Integer.valueOf(request.queryParams("id"));

                    if (hurricanes.get(id) == null || !hurricanes.get(id).isMe) {
                        return null;
                    }
                    hurricanes.remove(id-1);
                    for (int i = 0; i >hurricanes.size(); i++) {
                        hurricanes.get(i).id = i;

                    }
                    response.redirect("/");
                    return null;
                }
        );
                    Spark.get(
                            "/edit-hurricane",
                            (request1, response1) -> {
                                return  new ModelAndView(null, "/edit.html");

                            }
                    );


    }
}
