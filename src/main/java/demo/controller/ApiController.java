package demo.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import demo.common.json.CommonJson;

public class ApiController {

	private final Map<String, JsonSchema> schemaCache = new HashMap<>();
	private final ObjectMapper objectMapper = new ObjectMapper();

	protected String getJsonSchemaKey(HttpServletRequest request) {
		return request.getMethod() + " " + request.getServletPath();
	}

	protected CommonJson getRequestQuery(HttpServletRequest request) {

		Enumeration<String> pNames = request.getParameterNames();
		if (pNames == null) {
			return null;
		}
		CommonJson json = new CommonJson();
		while (pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			json.set(name, value);
		}
		return json;
	}

	protected CommonJson getFormmatedCommonJsonForValidation(CommonJson requestQuery, CommonJson requestBody) {
		CommonJson json = new CommonJson();
		if (requestQuery != null && !requestQuery.isEmpty()) {
			json.set("query", requestQuery);
		}
		if (requestBody != null) {
			json.set("body", requestBody);
		}
		return json;
	}

	private Set<ValidationMessage> validateRequestWithJsonSchema(String jsonSchemaKey, CommonJson formattedRequest) {
		JsonSchema schema = null;
		try {
			schema = getSchema(jsonSchemaKey);
		} catch (Exception e) {

		}
		JsonNode json = objectMapper.valueToTree(formattedRequest.props());
		return schema.validate(json);
	}

	private JsonSchema getSchema(String jsonSchemaKey) throws Exception {
		if (schemaCache.containsKey(jsonSchemaKey)) {
			return schemaCache.get(jsonSchemaKey);
		}

		// get mapping file
		File jsonSchemaMappingFile = ResourceUtils.getFile("classpath:json_schemas/index.json");
		String jsonSchemaMappingFileString = new String(Files.readAllBytes(jsonSchemaMappingFile.toPath()));

		String schemaFile = objectMapper.readTree(jsonSchemaMappingFileString).get(jsonSchemaKey).asText();

		// get schema file
		File jsonSchemaFile = ResourceUtils.getFile("classpath:json_schemas/" + schemaFile);
		InputStream schemaStream = Files.newInputStream(jsonSchemaFile.toPath());

		if (schemaStream == null) {
			throw new RuntimeException("Schema file not found: " + schemaFile);
		}

		JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaStream);

		// Put in cache
		schemaCache.put(jsonSchemaKey, schema);

		return schema;
	}

	protected void jsonSchemaValidate(HttpServletRequest request, CommonJson requestBody) {
		String jsonSchemaKey = getJsonSchemaKey(request);
		CommonJson requestQuery = getRequestQuery(request);
		CommonJson formattedRequestJson = getFormmatedCommonJsonForValidation(requestQuery, requestBody);
		Set<ValidationMessage> validationResult = validateRequestWithJsonSchema(jsonSchemaKey, formattedRequestJson);
		if (!validationResult.isEmpty()) {
			System.out.println(validationResult);
		}
	}

}
