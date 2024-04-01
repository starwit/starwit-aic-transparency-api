/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.2.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package de.starwit;

import de.starwit.aicockpit.Info;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-01T15:47:00.058415289+02:00[Europe/Berlin]")
@Validated
@Tag(name = "info", description = "the info API")
public interface InfoApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /info
     * shall deliver info on interface version and hosting system, readonly
     *
     * @return base info (status code 200)
     */
    @Operation(
        operationId = "getInfo",
        description = "shall deliver info on interface version and hosting system, readonly",
        responses = {
            @ApiResponse(responseCode = "200", description = "base info", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Info.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/info",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<Info>> getInfo(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"apiVersion\" : \"apiVersion\", \"generation-date\" : \"2000-01-23T04:56:07.000+00:00\", \"systemDescription\" : \"systemDescription\" }, { \"apiVersion\" : \"apiVersion\", \"generation-date\" : \"2000-01-23T04:56:07.000+00:00\", \"systemDescription\" : \"systemDescription\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}