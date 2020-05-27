package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import configuration.injectors.EndpointConfiguration;
import configuration.injectors.RouterConfiguration;
import configuration.routers.GraphQLRouter;

import static spark.Spark.get;
import static spark.Spark.post;


public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new EndpointConfiguration(),
                new RouterConfiguration()
        );

        post("/hola", (req, res) -> /*"hola");*/injector.getInstance(GraphQLRouter.class).postGraphQL(req, res));//,
        //injector.getInstance(ObjectMapper.class)::toJsonString);
    }
}
