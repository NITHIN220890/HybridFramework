package api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class ApiUtils {

    public static Map convertJSONFileToMap(String jsonPath) {
        Map<String, Object> map = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(Paths.get(jsonPath).toFile(), Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;

    }

    public static String buildPayloadUsingJSON(String payloadpath, Map testDataJson) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
        Template template = velocityEngine.getTemplate(payloadpath);
        VelocityContext velocitycontext = new VelocityContext(testDataJson);

        StringWriter stringWriter = new StringWriter();
        template.merge(velocitycontext, stringWriter);
        String jsonpayload = stringWriter.toString();
        return jsonpayload;

    }
}