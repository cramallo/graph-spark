package configuration.routers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entrypoints.GraphQLEndpoint;
import entrypoints.request.GraphQLEndpointRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GraphQLRouter {
    private static final Map<String, Object> EMPTY_VARIABLES = new HashMap<>();

    private ObjectMapper json;
    private GraphQLEndpoint endpoint;

    @Inject
    public GraphQLRouter(GraphQLEndpoint endpoint/*, Json json*/) {
        this.endpoint = endpoint;
        this.json = new ObjectMapper();
    }

    public Map<String, Object> postGraphQL(Request request, Response response) {

        Map<String, Object> context = new ConcurrentHashMap<>();

        response.type("application/json");
        response.status(HttpStatus.OK_200);
        StopWatch stopWatch = StopWatch.createStarted();

        String query = "";

        Map<String, Object> variables = EMPTY_VARIABLES;

        if (isJSONValid(request.body())) {

            try {
                Map<String, Object> payload = json.readValue(request.body(),
                        new TypeReference<Map<String, Object>>() {
                        });
                Map<String, Object> payloadVariables = json.readValue((String) payload.get("variables"),
                        new TypeReference<Map<String, Object>>() {
                        });
                query = payload.get("query").toString();
                variables = payloadVariables;
            } /*catch (JsonException e) {
                throw new ConfigurationException("Error parsing payload", e);
            }*/ catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            query = request.body();
        }

        GraphQLEndpointRequest model = GraphQLEndpointRequest.builder()
                .query(query)
                .variables(variables)
                .context(context)
                .build();

        //try {
        GraphQLEndpoint.Response endpointResponse = endpoint.post(model);
           /* if (endpointResponse.isPartial()) {
                response.status(HttpStatus.PARTIAL_CONTENT_206);
            }*/
            return endpointResponse.getResponse();
        /*} catch (BadRequestEndpointException br) {
            throw new BadRequestException(br.getMessage(), br);
        } catch (InternalServerEndpointException is) {
            throw new InternalServerErrorException(is.getMessage(), is);
        } finally {*/
        //stopWatch.stop();
            /*log.debug("Elapsed Time: " + stopWatch.getTime(TimeUnit.MILLISECONDS));
            token.expire();*/
        //}
    }


    /**
     * Checks if json is valid
     */
    private boolean isJSONValid(String jsonInString) {
        try {
            json.readValue(jsonInString, Object.class);
            return true;
        } /*catch (JsonException ignored) {
            return false;
        }*/ catch (JsonParseException e) {
            e.printStackTrace();
            return false;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
