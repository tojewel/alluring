package com.jcg.jaxb.json;


import akka.actor.ActorSystem;
import akka.dispatch.OnSuccess;
import akka.http.impl.server.MarshallerImpl;
import akka.http.impl.server.UnmarshallerImpl;
import akka.http.javadsl.Http;
import akka.http.javadsl.OutgoingConnection;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.marshallers.jackson.Jackson.*;
import akka.http.javadsl.model.*;
import akka.http.javadsl.server.Marshaller;
import akka.http.javadsl.server.Unmarshaller;
import akka.http.javadsl.server.Unmarshallers;
import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.Function1;
import scala.concurrent.Future;
import scala.runtime.BoxedUnit;

import static akka.http.javadsl.marshallers.jackson.Jackson.*;

public class TestClient {
    public static void main(String s[]) {
        //  get();
        post();
    }

    private static void get() {
        final ActorSystem system = ActorSystem.create();
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Flow<HttpRequest, HttpResponse, Future<OutgoingConnection>> connectionFlow = Http.get(system).outgoingConnection("localhost", 8080);

        HttpRequest httpRequest = HttpRequest.GET("/myfirstdb/myfirstcoll/55edc4a7d4c64492b5ffb66a");

        final Future<HttpResponse> f = Source.single(httpRequest).via(connectionFlow).runWith(Sink.<HttpResponse>head(), materializer);

        f.onSuccess(new OnSuccess<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse result) throws Throwable {
                ResponseEntity e = result.entity();

                Unmarshaller<Entity> un = jsonAs(Entity.class);
                System.out.print("un=" + un);

                System.out.println("result=" + result);
                System.out.println("result.entity()=" + e);
            }
        }, materializer.executionContext());

        System.out.println("done");

    }


    private static void post() {
        final ActorSystem system = ActorSystem.create();
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Flow<HttpRequest, HttpResponse, Future<OutgoingConnection>> connectionFlow = Http.get(system).outgoingConnection("localhost", 8080);


        HttpRequest httpRequest = HttpRequest.POST("/myfirstdb/myfirstcoll/")
                .withEntity(MediaTypes.APPLICATION_JSON.toContentType(), "{\"id\": 1, \"name\": \"giraffe\"}");

        final Future<HttpResponse> f = Source.single(httpRequest).via(connectionFlow).runWith(Sink.<HttpResponse>head(), materializer);

        f.onSuccess(new OnSuccess<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse result) throws Throwable {
                ResponseEntity e = result.entity();


                Marshaller<Entity> ma = Jackson.<Entity>json();

                MarshallerImpl<Entity> m = (MarshallerImpl<Entity>) ma;



                Unmarshaller<Entity> un = jsonAs(Entity.class);

                UnmarshallerImpl<Entity> u = (UnmarshallerImpl<Entity>) un;
                //

                System.out.print("un=" + un);

                System.out.println("result=" + result);
                System.out.println("result.entity()=" + e);


            }
        }, materializer.executionContext());


        System.out.println("done");

    }

}