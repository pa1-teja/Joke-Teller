package com.example.pavan.myapplication.backend;

import com.example.jokesLibrary;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import sun.rmi.runtime.Log;

/**
 * Created by pavan on 9/22/2016.
 */

@Api(
        name = "jokesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.pavan.example.com",
                ownerName = "backend.myapplication.pavan.example.com",
                packagePath=""
        )
)

    public class jokesApi{

         private jokesLibrary jokes= new jokesLibrary();
         private MyBean myBean = new MyBean();
         private int num = 1;

        @ApiMethod(name = "fetchJoke")
        public MyBean  fetchJoke(){
            System.out.println("request received");
            System.out.println("no.of jokes : " + jokes.getJokesList().size());
             myBean.setData(jokes.jokesList.get(num));
            return myBean;
        }
    }

