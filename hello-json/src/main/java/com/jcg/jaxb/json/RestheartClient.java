package com.jcg.jaxb.json;

import akka.actor.ActorSystem;
import akka.dispatch.OnSuccess;
import akka.http.javadsl.Http;
import akka.http.javadsl.OutgoingConnection;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.MediaTypes;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import ru.yandex.qatools.allure.model.TestCaseResult;
import scala.concurrent.Future;

import java.util.HashSet;
import java.util.Set;

import static akka.http.javadsl.model.MediaTypes.APPLICATION_JSON;

public class RESTHeartClient {
    final ActorSystem system;
    final ActorMaterializer materializer;
    final Flow<HttpRequest, HttpResponse, Future<OutgoingConnection>> connectionFlow;

    // Will come from outside
    final String database = "testdb";
    final Class<?>[] tables = {TestCaseResult.class};

    public RESTHeartClient() {
        system = ActorSystem.create();
        materializer = ActorMaterializer.create(system);
        connectionFlow = Http.get(system).outgoingConnection("localhost", 8080);

        // create database
        put("/" + database, "Test data database");

        // Create tables
        for (Class<?> table : tables) {
            put("/" + database + "/" + table.getSimpleName(), "Table created");
        }
    }

    public void save(String table, String data) {
        post("/" + database + "/" + table, data);
    }

    private void put(String url, String desc) {
        submit(HttpRequest
                .POST(url)
                .withEntity(APPLICATION_JSON.toContentType(), "{\"description\": \"" + desc + "\"}"));
    }

    private void post(String url, String data) {
        submit(HttpRequest
                .POST(url)
                .withEntity(APPLICATION_JSON.toContentType(), data));
    }

    private void submit(final HttpRequest httpRequest) {
        Source.single(httpRequest)
                .via(connectionFlow)
                .runWith(Sink.<HttpResponse>head(), materializer)
                .onSuccess(new OnSuccess<HttpResponse>() {
                    @Override
                    public void onSuccess(HttpResponse result) throws Throwable {
                        System.out.println("Successful: " + httpRequest);
                    }
                }, materializer.executionContext());

        System.out.println("Submited: " + httpRequest);
    }
}