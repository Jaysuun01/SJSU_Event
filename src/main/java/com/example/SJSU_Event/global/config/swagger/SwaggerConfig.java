package com.example.SJSU_Event.global.config.swagger;

import com.example.SJSU_Event.global.annotation.api.ApiErrorCodeExample;
import com.example.SJSU_Event.global.dto.ExampleHolder;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import com.example.SJSU_Event.global.exception.code.Reason;
import com.example.SJSU_Event.global.exception.dto.ApiResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        Info info = new Info()
                .version("v1.0.0")
                .title("SJSU_EVENT_PROJECT API Document")
                .description("""
                        ## SJSU_EVENT_PROJECT API 명세서입니다.
                                                
                        ---
                                                
                        ### 🔑 테스트 사용자 인증 토큰
                        `[null]`
                                                
                        """);

        String jwtSchemeName = "JWT";

        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("Authorization"));

        Server server = new Server().url("/");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .addServersItem(server)
                .components(components);
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiErrorCodeExample apiErrorCodeExample =
                    handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
            }
            return operation;
        };
    }

    private void generateErrorCodeResponseExample(
            Operation operation, ErrorStatus[] errorStatuses) {
        ApiResponses responses = operation.getResponses();
        List<ErrorStatus> showErrorStatus = new ArrayList<>();
        showErrorStatus.addAll(Arrays.asList(errorStatuses));

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
                showErrorStatus.stream()
                        .map(
                                errorStatus -> {
                                    Reason errorReason = errorStatus.getReason();
                                    return ExampleHolder.builder()
                                            .holder(
                                                    getSwaggerExample(
                                                            errorReason.getMessage(),
                                                            errorReason))
                                            .code(errorReason.getCode())
                                            .name(errorReason.getCode().toString())
                                            .build();
                                })
                        .collect(groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(String value, Reason errorReason) {
        ApiResponseDto<Object> responseDto = new ApiResponseDto<>(false, errorReason.getCode(), value, null);
        Example example = new Example();
        example.description(value);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonResponseDto = objectMapper.writeValueAsString(responseDto);
            example.setValue(jsonResponseDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return example;
    }

    private void addExamplesToResponses(
            ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();
                    v.forEach(
                            exampleHolder -> mediaType.addExamples(
                                    exampleHolder.getName(), exampleHolder.getHolder()));
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(status.toString(), apiResponse);
                });
    }
}