package com.example.pavan.myapplication.backend;

import com.example.jokesLibrary;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pavan on 9/24/2016.
 */
public class jokesServlet extends HttpServlet {

    private jokesLibrary jokes= new jokesLibrary();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        System.out.println("servlet received request");
        resp.getWriter().println(jokes.jokesList.get(1));
    }
}

